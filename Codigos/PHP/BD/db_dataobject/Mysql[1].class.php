<?php
// Classe de conexao mysql
// Criada em 27/02/2004 Por Vinícius Augusto Tagliatti Zani
// classe de uso privado, portanto PAGA
//  <viniciustz@mtv.com.br>

class Mysql {
    var $link;
    var $queryLink;
    var $host;
    var $username;
    var $password;
    var $query;
    var $where;
    var $limit;
    var $order;
    var $queryLink;
    var $table;
    var $database;
    var $finalQuery;
    var $error = array();
    
    /*  Construtor Mysql:
     *      abre uma nova conexao com o mysql
     *      retorna o link da conexao
     *  Exemplo: $obj = new Mysql("localhost", "root", "");
     */
    function Mysql($host, $usr, $pwd) {
        $this->host = $host;
        $this->username = $usr;
        $this->password = $pwd;
        $this->link = mysql_connect($this->host, $this->username, $this->password, true);
        return $this->link;
    }
    
    /*  Funcao assign:
     *      assinala uma conexao mysql ja aberta a este objeto
     *      retorna o link da conexao
     *  Exemplo: $obj->assign($my_link);
     */
    function assign($link) {
        $this->link = $link;
        return $this->link;
    }
    
    /*  Funcao selectDb:
     *      seleciona o banco de dados a ser usado pela classe
     *      retorna o resultado da funcao
     *  Exemplo: $msql->selectDb("usuarios");
     */
    function selectDb($db) {
        $this->database = $db;
        return mysql_select_db($this->database, $this->link);
    }
    
    /*  Funcao query:
     *      adiciona sua query ao objeto, e executa, se for especificado
     *  Exemplo:    $obj->whereAdd("usr_nome = '".$obj->escape($nome)."'");
     *              $obj->query("SELECT * FROM usuarios", false);
     *              $obj->find();
     *      retorna o link para a query se for executado, ou true caso contrario
     */
    function query($query, $exec = true) {
        $this->query = $query;
        if ($exec) {
            return $this->find();
        } else {
            return true;
        }
    }
    
    /*  Funcao find:
     *      executa a query armazanada no objeto
     *      retorna o link da query
     *  Exemplo: $obj->find();
     */
    function find() {
        $this->mountQuery();
        $this->queryLink = mysql_query($this->finalQuery, $this->link);
        if ($this->queryLink == false) {
            $this->setError("Erro na query: ", true, true);
        }

        return $this->queryLink;
    }
    
    /*  Funcao mountQuery:
     *      monta sua query com os dados atuais do objeto
     *      retorna o texto da query
     *  Exemplo: $obj->whereAdd("YEAR(data) = YEAR(NOW())");
     *           $str = $obj->mountQuery();
     */
    function mountQuery() {
        $this->finalQuery = $this->query . $this->where . $this->order . $this->limit;
        if ($this->finalQuery[sizeof($this->finalQuery)-1] != ";")
            $this->finalQuery .= ";";
            
        return $this->finalQuery;
    }
    
    /*  Funcao count:
     *      conta o numero de resultados da ultima query
     *      retorna o numer de resultados
     *  Exemplo: $num_res = $obj->count();
     */
    function count() {
        return mysql_num_rows($this->queryLink);
    }
    
    /*  Funcao limit:
     *      limita o resultado de pesquisas ($pesq->limit(0, 10))
     *      ou de insercoes, delecoes ($pesq->limit(1))
     */
    function limit($start, $end = "") {
      if (!empty($this->query)) {
        if (ereg("limit", strtolower($this->query))) {
            $this->setError("Limit ja existente!");
            return false;
        }
      }
        if (!empty($end))
            $this->limit = " LIMIT ".$start.", ".$end;
        else
            $this->limit = " LIMIT ".$start;
        return true;
    }
    
    /*  Funcao whereAdd:
     *      adiciona na sua query uma limitacao de pesquisa where
     *      multiplos where sao permitidos
     */
    function whereAdd($condition) {
      if (!empty($this->where)) {
        if (ereg("where", strtolower($this->where)))
            $this->where .= " AND ( ".$condition.")";
        else
            $this->where = " WHERE ( ".$condition." )";
      } else {
            $this->where = " WHERE ( ".$condition." )";
      }
    }
    
    /*  Funcao orderBy:
     *      adiciona na sua query uma ordenacao
     *      multiplas adicoes sao validas (varios orderBy's chamados)
     */
    function orderBy($field) {
        $type = false;
        $search = array("ASC", "DESC");
        foreach ($search as $val) {
            if (ereg($val, strtoupper($field)))
                $type = true;
        }
        if (!empty($this->order)) {
            $this->order .= " AND ".$field.($type == true ? "" : " ASC");
        } else {
            $this->order .= " ORDER BY ".$field.($type == true ? "" : " ASC");
        }
        return true;
    }
    
    /*  Funcao fetch:
     *      retorna um objeto da query atual
     *  Exemplo: $usuraio = $query->fetch();
     */
    function fetch() {
        return mysql_fetch_object($this->queryLink);
    }
    
    /*  Funcao fetchToThis:
     *      retorna o ojeto atual com as variaveis do BD
     *  Exemplo: $usuario->fetchToThis();
     *           echo $usuario->usr_nome;
     */
    function fetchToThis() {
        if ($tmp = mysql_fetch_assoc($this->queryLink)) {
            while (list($name, $val) = each($tmp)) {
                $this->{$name} = $val;
            }
            return $this;
        }
        return false;
    }
    
    /*  Funcao escape:
     *      escapa uma string para uso com mysql
     *      retorna a string escapada
     *  Exemplo: $str = $mysql_obj->escape($str);
     */
    function escape($str) {
        $notAllowed = array("_", "<", ">");
        $replace = array("", "", "");
        $str = preg_replace($notAllowed, $replace, $str);
        if (function_exists('mysql_escape_string')) {
            return mysql_escape_string($str);
        } else {
            return addslashes($str);
        }
    }

    /*  Funcao setError:
     *      seta o erro do mysql
     *      se debug = true, mostra detalhes do objeto (query, etc)
     *      retorna true
     */
    function setError($err, $autoshow = true, $debug=false) {
        if (!$debug) {
            $this->error[] = "Error ".$err."<BR>" . mysql_error($this->link);
        } else {
            $this->error[] = "Erro : " .
                             $err ."<BR>" .
                             "Erro mysql: ".mysql_error($this->link)."<BR><BR>".
                             "Query atual: ".$this->finalQuery."<BR>".
                             "Database: ".$this->database."<BR>Tabela: ".$this->table."<BR>";
        }
        if ($autoshow)
            $this->showErrors();
    }
    
    /*  Funcao showErrors:
     *      mostra os erros ocorridos com queryes e seus detalhes
     *      retorna true
     *  Exemplo: $obj->showErrors(true);
     */
    function showErrors($die=false) {
        if (sizeof($this->error) > 0) {
            foreach ($this->error as $err) {
                echo $err."<BR><BR>";
            }
            if ($die)
                die;
        }
    }
}

?>
