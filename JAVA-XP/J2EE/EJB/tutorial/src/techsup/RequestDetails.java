public class RequestDetails implements Comparable {
  private int requestId = 0;
  private String nome = null;
  private String sobrenome = null;
  private String email = null;
  private String fone = null;
  private String software = null;
  private String so = null;
  private String problema = null;
    
  public RequestDetails(int requestId, String nome,
                        String sobrenome, String email,
                        String fone, String software,
                        String so, String problema) {

    this.requestId = requestId;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.fone = fone;
    this.software = software;
    this.so = so;
    this.problema = problema;
  }

  public int getRequestId() {
    return requestId;
  }

  public String getNome() {
    return nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }

  public String getEmail() {
    return email;
  }

  public String getFone() {
    return fone;
  }

  public String getSoftware() {
    return software;
  }

  public String getSo() {
    return so;
  }

  public String getProblema() {
    return problema;
  }

  public int compareTo(Object o) {
    RequestDetails n = (RequestDetails)o;
    return this.requestId - n.requestId;
  }
}

