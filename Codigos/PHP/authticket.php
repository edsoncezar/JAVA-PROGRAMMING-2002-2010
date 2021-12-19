//**************************************
    //     
    // Name: authticket
    // Description:This code generates an MD
    //     5 protected string, which can be used to
    //     hand off to other web pages, or even oth
    //     er sites. If someone can read the ticket
    //     , they can use it, so this works best ov
    //     er encrypted connections, but since the 
    //     ticket only lasts for a short time (2 ho
    //     urs, definable) I think it's better than
    //     sending a password around. By Michael Gr
    //     aff.
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?php
    // $Id: authticket.phl,v 1.3 1998/02/11 
    //     16:45:34 explorer Exp $
    //
    // Copyright (c) 1998 Michael Graff <
    //     explorer@flame.org>
    // All rights reserved.
    //
    // Redistribution and use in source and 
    //     binary forms, with or without
    // modification, are permitted provided 
    //     that the following conditions
    // are met:
    // 1. Redistributions of source code mus
    //     t retain the above copyright
    //notice, this list of conditions and th
    //     e following disclaimer.
    // 2. Redistributions in binary form mus
    //     t reproduce the above copyright
    //notice, this list of conditions and th
    //     e following disclaimer in the
    //documentation and/or other materials p
    //     rovided with the distribution.
    // 3. Neither the name of author nor the
    //     names of its contributors may be
    //used to endorse or promote products de
    //     rived from this software
    //without specific prior written permiss
    //     ion.
    //
    // THIS SOFTWARE IS PROVIDED BY THE AUTH
    //     OR AND CONTRIBUTORS ``AS IS'' AND ANY
    // EXPRESS OR IMPLIED WARRANTIES, INCLUD
    //     ING, BUT NOT LIMITED TO, THE IMPLIED
    // WARRANTIES OF MERCHANTABILITY AND FIT
    //     NESS FOR A PARTICULAR PURPOSE ARE
    // DISCLAIMED. IN NO EVENT SHALL THE FOU
    //     NDATION OR CONTRIBUTORS BE LIABLE
    // FOR ANY DIRECT, INDIRECT, INCIDENTAL,
    //     SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
    // DAMAGES (INCLUDING, BUT NOT LIMITED T
    //     O, PROCUREMENT OF SUBSTITUTE GOODS OR
    // SERVICES; LOSS OF USE, DATA, OR PROFI
    //     TS; OR BUSINESS INTERRUPTION) HOWEVER
    // CAUSED AND ON ANY THEORY OF LIABILITY
    //     , WHETHER IN CONTRACT, STRICT
    // LIABILITY, OR TORT (INCLUDING NEGLIGE
    //     NCE OR OTHERWISE) ARISING IN ANY WAY
    // OUT OF THE USE OF THIS SOFTWARE, EVEN
    //     IF ADVISED OF THE POSSIBILITY OF
    // SUCH DAMAGE.
    //
    class authticket {
    	var $secret = "setme";		// you WILL want to change this
    	var $realm = "";		// the realm of this identity
    	var $lifetime = 2 * 60 *60;	// tickets good for 2 hours
    	var $authenticated = 0;		// the data here is valid iff non-zero
    	var $identity;		// the remote identity, if decoded correctly
    	var $issue;		// the time the ticket was issued
    	var $remote_addr;	// the remote address of the client
    	var $hash;		// the hash value. Probably of little use.
    	var $autherr;		// if verification faild, this contains why
    	//
    	// helper function which just zeros out the ticket data
    	//
    	function zerodata()
    	{
    		$this->authenticated = 0;
    		$this->identity = "";
    		$this->issue = 0;
    		$this->remote_addr = "";
    		$this->hash = "";
    		$this->autherr = "";
    	}
    	//
    	// Take a string ($identity) and a time ($time) and the internal
    	// secret value, and generate a string that can be used to verify
    	// that the remote user is known to us. The result of this function
    	// is a single string, that can be passed along in a hidden form
    	// or even a cookie.
    	//
    	// If ($time) is 0, the current time is used instead.
    	//
    	// ($identity) _cannot_ contain a ``:'' character. If you need
    	// one in there, you will have to change it to some sort of escape
    	// sequence.
    	//
    	// Some care should be used. I recommend using this only over SSL,
    	// unless the actual ticket contents are encrypted using something
    	// stronger than XOR.
    	//
    	function makeauth($identity, $time)
    	{
    		global $REMOTE_ADDR;
    		$this->zerodata();
    		if ($time == 0)
    			$time = time();
    		$ticket_items[] = (string)$time;
    		$ticket_items[] = $this->realm;
    		$ticket_items[] = $REMOTE_ADDR;
    		$ticket_items[] = $identity;
    		$ticket = implode($ticket_items, ":");
    		$hash = md5($this->secret . $ticket);
    		$ticket = $hash . ':' . $ticket;
    		$this->identity = $identity;
    		$this->issue = $time;
    		$this->remote_addr = $REMOTE_ADDR;
    		$this->hash = $hash;
    		$this->authenticated = 1; /* data is valid */
    		$this->autherr = "";
    		return $ticket;
    	}
    	//
    	// Take a ($ticket) string generated by makeauth(), and a ($time),
    	// and verify that the ticket is valid and not expired.
    	//
    	// If ($time) is 0, the current time will be used.
    	//
    	// On error, the function returns the empty string "",
    	// $authenticated is 0, and $autherr contains the reason
    	// the authentication failed.
    	//
    	// On success, the identity encoded in the ticket is returned,
    	// $authenticated is non-zero, and $autherr is to be ignored.
    	//
    	function checkauth($ticket, $time)
    	{
    		global $REMOTE_ADDR;
    		$this->zerodata();
    		if ($time == 0)
    			$time = time();
    		/*
    		 * Item order: hash time realm remote_addr identity
    		 */
    		$ticket_items = explode(":", $ticket);
    		/*
    		 * if the remote address doesn't match the one in the ticket,
    		 * drop them.
    		 */
    		if ($ticket_items[3] != $REMOTE_ADDR) {
    			$this->autherr = "Address mismatch";
    			return "";
    		}
    		//
    		// if we are supposed to check for expired tickets, do that
    		// here.
    		//
    		if ($this->lifetime != 0)
    			if ($time > (int)$ticket_items[1] + $this->lifetime) {
    				$this->autherr = "Ticket expired";
    				return "";
    			}
    		//
    		// make certain that the ticket is not being used before
    		// it was issued.
    		//
    		if ($time < (int)$ticket_items[1]) {
    			$this->autherr = "Ticket used before issued";
    			return "";
    		}
    		//
    		// verify that the realms match
    		//
    		if ($this->realm != $ticket_items[2]) {
    			$this->autherr = "Realm mismatch";
    			return "";
    		}
    		//
    		// This could be done better... Reassemble the components
    		// of the ticket passed to us, and rehash. Compare this
    		// to the hash we were sent.
    		//
    		$tmp_items[] = $ticket_items[1];
    		$tmp_items[] = $ticket_items[2];
    		$tmp_items[] = $ticket_items[3];
    		$tmp_items[] = $ticket_items[4];
    		$tmp_ticket = implode($tmp_items, ":");
    		$hash = md5($this->secret . $tmp_ticket);
    		if ($hash != $ticket_items[0]) {
    			$this->autherr = "Integrity check failed";
    			return "";
    		}
    		//
    		// well, it all checks out. Might as well claim we know
    		// who this person is.
    		//
    		$this->hash = $hash;
    		$this->issue = $ticket_items[1];
    		$this->remote_addr = $ticket_items[3];
    		$this->identity = $ticket_items[4];
    		$this->authenticated = 1;
    		return $this->identity;
    	}
    };
    ?>

