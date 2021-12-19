package br.com.easynet.ide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import javax.naming.*;

/**
 * Title: Gerador de EntityBean
 * Description: Aplicação para geração de Entity Beans a partir do Banco de Dados
 * Copyright:    Copyright (c) 2002
 * Company: EasyNet
 * @author José Maria Rodrigues Santos Júnior - www.unit.br/zemaria
 * @version 1.0
 */

public class EntityBeanGenerator extends javax.swing.JFrame {
  /**
   * Objetos para conexão com o SGBD
   */
  Connection connection;
  /**
   * Linha do log
   */
  int linha = 0;

  JPanel jPanel1 = new JPanel();
  JLabel jLabelDriver = new JLabel();
  JTextField jTextFieldDriver = new JTextField();
  JLabel jLabelURL = new JLabel();
  JTextField jTextFieldURL = new JTextField();
  JLabel jLabelUsuario = new JLabel();
  JTextField jTextFieldUsuario = new JTextField();
  JLabel jLabelSenha = new JLabel();
  JPasswordField jPasswordFieldSenha = new JPasswordField();
  JButton jButtonConectar = new JButton();
  JTextArea jTextAreaLog = new JTextArea();
  JButton jButtonEntityBeans = new JButton();
  JButton jButtonDesconectar = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JLabel jLabelPacote = new JLabel();
  JTextField jTextFieldPacote = new JTextField();
  JButton jButtonSair = new JButton();
  JLabel jLabelDir = new JLabel();
  JTextField jTextFieldDir = new JTextField();
  JLabel jLabelNomeEJB = new JLabel();
  JTextField jTextFieldNomeEJB = new JTextField();
  JLabel jLabelDataSource = new JLabel();
  JTextField jTextFieldDataSource = new JTextField();
  JLabel jLabelWL_HOME = new JLabel();
  JTextField jTextFieldWL_HOME = new JTextField();

  public EntityBeanGenerator() {
    try {
      jbInit();
      this.setSize(500,500);
      this.show();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    EntityBeanGenerator entityBeanGenerator = new EntityBeanGenerator();
  }
  private void jbInit() throws Exception {
    jPanel1.setLayout(null);
    jLabelDriver.setText("Driver");
    jLabelDriver.setBounds(new Rectangle(30, 9, 37, 17));
    jTextFieldDriver.setToolTipText("Nome do driver JDBC a ser utilizado na conexão com o SGBD");
    jTextFieldDriver.setText("com.microsoft.jdbc.sqlserver.SQLServerDriver");
    jTextFieldDriver.setBounds(new Rectangle(68, 8, 413, 21));
    jLabelURL.setText("URL");
    jLabelURL.setBounds(new Rectangle(41, 32, 26, 17));
    jTextFieldURL.setToolTipText("URL do Banco de dados");
    jTextFieldURL.setText("jdbc:microsoft:sqlserver://localhost:1433");
    jTextFieldURL.setBounds(new Rectangle(68, 32, 413, 21));
    jLabelUsuario.setText("Usuário");
    jLabelUsuario.setBounds(new Rectangle(20, 57, 47, 17));
    jTextFieldUsuario.setToolTipText("Usuário");
    jTextFieldUsuario.setText("Vestibular");
    jTextFieldUsuario.setBounds(new Rectangle(68, 55, 216, 21));
    jLabelSenha.setText("Senha");
    jLabelSenha.setBounds(new Rectangle(288, 58, 41, 17));
    jPasswordFieldSenha.setToolTipText("Senha");
    jPasswordFieldSenha.setText("Vestibular");
    jPasswordFieldSenha.setBounds(new Rectangle(342, 56, 139, 21));
    jButtonConectar.setText("Conectar");
    jButtonConectar.setBounds(new Rectangle(5, 184, 87, 27));
    jButtonConectar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonConectar_actionPerformed(e);
      }
    });
    jTextAreaLog.setToolTipText("Log de operações realizadas");
    jTextAreaLog.setEditable(false);
    jTextAreaLog.setLineWrap(true);
    jTextAreaLog.setTabSize(2);
    jTextAreaLog.setWrapStyleWord(true);
    this.setDefaultCloseOperation(3);
    this.setResizable(false);
    this.setTitle("EasyNet - Gerador de Entity Bean");
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    jButtonEntityBeans.setEnabled(false);
    jButtonEntityBeans.setToolTipText("");
    jButtonEntityBeans.setText("EntityBeans");
    jButtonEntityBeans.setBounds(new Rectangle(204, 184, 103, 27));
    jButtonEntityBeans.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonEntityBeans_actionPerformed(e);
      }
    });
    jButtonDesconectar.setEnabled(false);
    jButtonDesconectar.setText("Desconectar");
    jButtonDesconectar.setBounds(new Rectangle(94, 184, 109, 27));
    jButtonDesconectar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDesconectar_actionPerformed(e);
      }
    });
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setBounds(new Rectangle(8, 219, 475, 239));
    jLabelPacote.setText("Pacote");
    jLabelPacote.setBounds(new Rectangle(26, 79, 41, 17));
    jTextFieldPacote.setToolTipText("Pacote ser utilizado na criação das interfaces e classes dos Entity " +
    "Beans");
    jTextFieldPacote.setText("br.com.easyNet.vestibular.ejb");
    jTextFieldPacote.setBounds(new Rectangle(68, 79, 413, 21));
    jButtonSair.setText("Sair");
    jButtonSair.setBounds(new Rectangle(390, 184, 81, 27));
    jButtonSair.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSair_actionPerformed(e);
      }
    });
    jLabelDir.setText("Diretório");
    jLabelDir.setBounds(new Rectangle(9, 106, 58, 17));
    jTextFieldDir.setToolTipText("Diretório onde serão salvos os arquivos gerados.");
    jTextFieldDir.setText("d:\\VestibularEasyNet\\src");
    jTextFieldDir.setBounds(new Rectangle(68, 103, 413, 21));
    jLabelNomeEJB.setText("Nome EJB");
    jLabelNomeEJB.setBounds(new Rectangle(6, 128, 61, 17));
    jTextFieldNomeEJB.setToolTipText("Nome EJB para o conjunto de Entity Beans gerados");
    jTextFieldNomeEJB.setText("VestibularEasyNet");
    jTextFieldNomeEJB.setBounds(new Rectangle(68, 126, 158, 21));
    jLabelDataSource.setToolTipText("");
    jLabelDataSource.setText("DataSource");
    jLabelDataSource.setBounds(new Rectangle(239, 128, 72, 17));
    jTextFieldDataSource.setToolTipText("DataSource do servidor EJB a ser utilizado pelos Enity Beans");
    jTextFieldDataSource.setText("vestibular.DataSource");
    jTextFieldDataSource.setBounds(new Rectangle(313, 126, 168, 21));
    jLabelWL_HOME.setText("WL_HOME");
    jLabelWL_HOME.setBounds(new Rectangle(1, 153, 66, 17));
    jTextFieldWL_HOME.setText("D:/DevTools/Bea/wlserver6.1");
    jTextFieldWL_HOME.setBounds(new Rectangle(68, 152, 413, 21));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabelDriver, null);
    jPanel1.add(jTextFieldDriver, null);
    jPanel1.add(jTextFieldURL, null);
    jPanel1.add(jLabelURL, null);
    jPanel1.add(jTextFieldUsuario, null);
    jPanel1.add(jLabelUsuario, null);
    jPanel1.add(jLabelPacote, null);
    jPanel1.add(jTextFieldPacote, null);
    jPanel1.add(jPasswordFieldSenha, null);
    jPanel1.add(jLabelSenha, null);
    jPanel1.add(jScrollPane1, null);
    jPanel1.add(jTextFieldDir, null);
    jPanel1.add(jLabelDir, null);
    jPanel1.add(jLabelNomeEJB, null);
    jPanel1.add(jLabelDataSource, null);
    jPanel1.add(jTextFieldNomeEJB, null);
    jPanel1.add(jTextFieldDataSource, null);
    jPanel1.add(jButtonConectar, null);
    jPanel1.add(jButtonSair, null);
    jPanel1.add(jButtonDesconectar, null);
    jPanel1.add(jButtonEntityBeans, null);
    jPanel1.add(jTextFieldWL_HOME, null);
    jPanel1.add(jLabelWL_HOME, null);
    jScrollPane1.getViewport().add(jTextAreaLog, null);
  }

  void jButtonConectar_actionPerformed(ActionEvent e) {
 		try {
			Class.forName( jTextFieldDriver.getText() );
      addLog("Driver JDBC : " + jTextFieldDriver.getText() +
             " carregado.");
		} catch ( ClassNotFoundException cnfe ) {
      addLog("Driver JDBC não encontrado : " + cnfe.getMessage() );
		}
		try {
			/* Obtendo a conexão com o banco de dados */
			connection =
        DriverManager.getConnection( jTextFieldURL.getText(),
                                     jTextFieldUsuario.getText(),
                                     new String(jPasswordFieldSenha.getPassword()) );
			addLog( "Conexão com o banco de dados estabelecida." );
      jButtonConectar.setEnabled(false);
      jButtonDesconectar.setEnabled(true);
      jButtonEntityBeans.setEnabled(true);
		} catch ( SQLException sqle ) {
			addLog( "Erro na conexão ao Bando de Dados : " + sqle.getMessage() );
		}
  }

  private void close () {
    try {
      connection.close();
      jButtonConectar.setEnabled(true);
      jButtonDesconectar.setEnabled(false);
      jButtonEntityBeans.setEnabled(false);
      addLog("Conexão fechada.");
    } catch (Exception exception) {
      addLog("Erro no fechamento da conexão: " + exception.getMessage());
    }
  }

  /**
   * Método para adicionar mensagens ao log
   * @param msg Mensagem a ser adicionada ao log
   */
  private void addLog (String msg) {
    jTextAreaLog.append(++linha + " => " + msg + "\n");
  }

  void jButtonDesconectar_actionPerformed(ActionEvent e) {
    close();
  }

  void this_windowClosing(WindowEvent e) {
    close();
  }

  /**
   * Método para iniciar a classe abstrata, colocando as definições de package,
   * imports, a assinatura da classe e o construtor padrão
   * @param beanName Nome do EnityBean
   * @param packageName nome do pacote
   * @param bc Classe abstrata a ser gerada
   */
  private void beginBeanClass (String beanName, String packageName,
                               Vector rels1, Vector relsN,
                               StringBuffer bc) {
    bc.append("package " + packageName + "." + firstLetterToLowerCase(beanName) + ";\n\n");
    bc.append("import java.util.*;\n");
    bc.append("import javax.ejb.*;\n");
    bc.append("import java.rmi.RemoteException;\n");
    bc.append("import javax.naming.*;\n\n");
    /* Importando as classes que serão utilizadas nos métodos de negócio */
    for (int i = 0; i < rels1.size(); i++) {
      String tableName = (String) rels1.get(i);
      bc.append("import " + packageName + "." +
                 firstLetterToLowerCase(tableName) +
                 "." + tableName +";\n");
    }
    for (int i = 0; i < relsN.size(); i++) {
      String tableName = (String) relsN.get(i);
      bc.append("import " + packageName + "." +
                 firstLetterToLowerCase(tableName) +
                 "." + tableName +";\n");
      bc.append("import " + packageName + "." +
                 firstLetterToLowerCase(tableName) +
                 "." + tableName +"Home;\n");
      bc.append("import " + packageName + "." +
                 firstLetterToLowerCase(tableName) +
                 "." + tableName +"PK;\n\n");
    }
    bc.append("public abstract class " + beanName + "Bean implements EntityBean {\n\n");
    bc.append("\tprivate EntityContext ctx;\n\n");
    bc.append("\tpublic " + beanName + "Bean () {}\n\n");
  }

  /**
   * Método para inserir os métodos set e get para os atributos CMP na classe abstrata
   * @param bc Classe abstrata
   * @param fields Vector com a realação de campos
   * @see br.com.easynet.ide.Field
   */
  private void addFieldsToBeanClass (StringBuffer bc, Vector fields) {
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      addFieldToBeanClass(bc, field.name, field.typeName);
    }
  }

  /**
   * Método para inserir o par de métodos set/get para um atributo
   * @param bc Bean class
   * @param fn Nome do atributo
   * @param ft Tipo do atributo
   */
  private void addFieldToBeanClass (StringBuffer bc, String fn, String ft) {
    fn = firstLetterToUpperCase(fn);
    bc.append("\tpublic abstract void set" + fn + " (" + ft + " value);\n");
    bc.append("\tpublic abstract " + ft + " get" + fn + " ();\n\n");
  }

  /**
   * Método para obter a relação de campos requeridos no formato de parametros
   * @param fields Vector com a relação de campos
   * @return relação de campos requeridos no formato de parâmteros. ex. "(String codigo,float salario)"
   * @see br.com.easynet.ide.Field
   */
  private String getParamListForRequiredFields (Vector fields) {
    StringBuffer params = new StringBuffer("");
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      if (field.nullable == DatabaseMetaData.columnNoNulls) {
        params.append(field.typeName + " " + field.name + ",");
      }
    }
    /* Removendo a última vírgula */
    params.deleteCharAt(params.length() - 1);
    return params.toString();
  }

  /**
   * Método para obter a relação de campos separados por vírgulas, no formato de
   * chamada de método
   * @param fields Vector com a relação de campos
   * @return relação de campos requeridos no formato de chamada de método. ex. "(codigo,salario)"
   * @see br.com.easynet.ide.Field
   */
  private String getParamListCallWithRequiredFields (Vector fields) {
    StringBuffer params = new StringBuffer("");
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      if (field.nullable == DatabaseMetaData.columnNoNulls) {
        params.append(field.name + ",");
      }
    }
    /* Removendo a última vírgula */
    params.deleteCharAt(params.length() - 1);
    return params.toString();
  }

  /**
   * Método para adicionar os métodos set e get para os relacionamentos 1x1
   * @param relations Tabelas relacionadas
   * @param bc Classe abstrata
   */
  private void addRelations1ToBeanClass (Vector relations, StringBuffer bc) {
    Iterator i = relations.iterator();
    while (i.hasNext()) {
      String tableName = (String) i.next();
      bc.append("\tpublic abstract void set" + tableName + " (" + tableName + " value);\n");
      bc.append("\tpublic abstract " + tableName + " get" + tableName + " ();\n\n");
    }
  }

  /**
   * Método para adicionar os métodos set e get para os relacionamentos 1xN.
   * @param relations Tabelas relacionadas
   * @param bc Classe abstrata
   */
  private void addRelationsNToBeanClass (Vector relations, StringBuffer bc) {
    Iterator i = relations.iterator();
    while (i.hasNext()) {
      String tableName = (String) i.next();
      bc.append("\tpublic abstract void set" + tableName + " (Set value);\n");
      bc.append("\tpublic abstract Set get" + tableName + " ();\n\n");
    }
  }


  private void addToXmlToBeanClass (Vector fields, String beanName, StringBuffer bc) {
    bc.append("\tpublic String toXml() {\n");
    bc.append("\t\tStringBuffer sb = new StringBuffer();\n");
    bc.append("\t\tsb.append(\"<" + beanName + ">\\n\");\n");
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      bc.append("\t\tsb.append(\"\\t<" + field.name + ">\" + get" +
                firstLetterToUpperCase(field.name) + "() + \"</" +
                field.name + ">\\n\");\n");
    }
    bc.append("\t\tsb.append(\"</" + beanName + ">\\n\");\n");
    bc.append("\t\treturn sb.toString();\n");
    bc.append("\t}\n\n");
  }

  /**
   * Método para inserir os métodos ejcCreate e ejbPosCreate tendo como parâmetros os campos requeridos
   * @param beanName Nome do bean
   * @param fields Campos do bean
   * @param bc Classe abstrata
   */
  private void addEjbCreateMethodsToBeanClass (String beanName, Vector fields, StringBuffer bc) {
    /* Método ejbCreate com todos os campos obrigatórios */
    StringBuffer ejbCreate = new StringBuffer();
    /* Método ejbPosCreate correspondente ao método ejbCreate acima */
    StringBuffer ejbPosCreate = new StringBuffer();
    /* Iniciando os métodos */
    ejbCreate.append("\tpublic " + beanName + "PK ejbCreate ");
    ejbPosCreate.append("\tpublic void ejbPostCreate ");
    /* Obtendo a lista de parâmetro para os métodos ejbCreate e ejbPosCreate */
    String params = "(" + getParamListForRequiredFields(fields) + ")";
    /* Inserindo a lista de parâmetros a assinatura do método ejbCreate */
    ejbCreate.append(params + "\n");
    /* Finalizando a assinatura do método ejbCreate */
    ejbCreate.append("\t\tthrows CreateException {\n");
    /* Inserindo a lista de parâmetros à assinatura do método ejbPosCreate */
    ejbPosCreate.append(params + "\n");
    /* Finalizando a assinatura do método ejbPosCreate */
    ejbPosCreate.append("\t\tthrows CreateException {}\n");
    /* Obtendo a relação de métodos set para os campos dos métodos ejbCreate */
    StringBuffer sets = new StringBuffer("");
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      if (field.nullable == DatabaseMetaData.columnNoNulls) {
        sets.append("\t\tset" + firstLetterToUpperCase(field.name) +
                    "(" + field.name + ");\n");
      }
    }
    /* Inserindo os método set para os campos recebidos como parametro */
    ejbCreate.append(sets);
    /* Finalizando o método ejbCreate */
    ejbCreate.append("\t\treturn null;\n");
    ejbCreate.append("\t}");
    /* Inserindo os métodos na classe abstrata do Entity Bean */
    bc.append(ejbCreate.toString() + "\n\n");
    bc.append(ejbPosCreate.toString() + "\n\n");
  }

  /**
   * Método para inserir métodos de negócios para fornecer à camada cliente a
   * possibilidade de recuperar EntityBeans que tenham relacionamentos 1. Pois
   * os métodos set e get para os relacionamentos não podem ser oferdados para a
   * camada cliente
   * O método terá o seguinte nome: getFromRelation[Nome do Bean relacionado] e
   * irá retorna a interface remota do bean relacionado, através do método get
   * do próprio relacionamento.
   * @param tables Vector com as tabelas relacionadas
   * @param bc Classe abstrata do Bean
   */
  private void addRelations1BusinessMethodsToBeanClass(Vector tables, StringBuffer bc) {
    Iterator i = tables.iterator();
    while (i.hasNext()) {
      String tableName = (String) i.next();
      bc.append("\tpublic " + tableName + " getFromRelation" + tableName + "()\n");
      bc.append("\t\t\tthrows FinderException {\n");
      bc.append("\t\t return get" + tableName + "();\n");
      bc.append("\t}\n\n");
    }
  }

  /**
   * Método para inserir métodos de negócios para fornecer à camada cliente a
   * possibilidade de inserir, recuperar e apagar EntityBeans que tenham
   * relacionamentos N.
   * Serão inserindo 3 métodos chamados de:<br
   * create[Nome do Bean](campos requeridos),
   * getFromRelationShip[Nome do Bean](),
   * remove[Nome do Bean](campos da chave primária).
   * @param tables Bean com relacionamento N
   * @param fields Vector com os campos (br.com.easynet.ide.Field) de todas as tabelas
   * @param pkFields Vector com os campos (br.com.easynet.ide.Field) chaves de todas as tabelas
   * @param bc Classe Abstrata do Bean
   * @see br.com.easynet.ide.Field
   */
  private void addRelationsNBusinessMethodsToBeanClass(Vector tables,
                                                       Vector tablesReal,
                                                       Vector tablesFields,
                                                       Vector tablesPkFields,
                                                       String EJBName,
                                                       StringBuffer bc) {
    EJBName = firstLetterToLowerCase(EJBName);
    for (int i = 0; i < tablesReal.size(); i++) {
      String beanName = (String) tablesReal.get(i);
      int posTableRel = tables.indexOf(beanName);
      /* Método create */
      bc.append("\tpublic " + beanName + " create" + beanName +
                "(" +
                getParamListForRequiredFields((Vector)tablesFields.get(posTableRel)) +
                ")\n");
      bc.append("\t\tthrows Exception {\n");
      bc.append("\t\ttry {\n");
      bc.append("\t\t\tContext ctx = new InitialContext();\n");
      bc.append("\t\t\t" + beanName + "Home ejbHome = (" + beanName +
                "Home) ctx.lookup(\"" + EJBName + "." + beanName + "EJB\");\n");
      bc.append("\t\t\t" + beanName + " ejb = ejbHome.create(" +
                getParamListCallWithRequiredFields((Vector)tablesFields.get(posTableRel)) +
                ");\n");
      bc.append("\t\t\tget" + beanName + "().add(ejb);\n");
      bc.append("\t\t\treturn ejb;\n");
      bc.append("\t\t} catch (Exception e) {\n");
      bc.append("\t\t\t throw new Exception(e.getMessage());\n");
      bc.append("\t\t}\n");
      bc.append("\t}\n\n");
      /* Método getFromRelationShip */
      bc.append("\tpublic Set getFromRelationShip" + beanName + " () {\n");
      bc.append("\t\tHashSet hs = new HashSet();\n");
      bc.append("\t\tIterator i = get" + beanName + "().iterator();\n");
      bc.append("\t\twhile (i.hasNext()) {\n");
      bc.append("\t\t\ths.add(i.next());\n");
      bc.append("\t\t}\n");
      bc.append("\t\treturn (Set) hs;\n");
      bc.append("\t}\n\n");
      /* Método remove */
      bc.append("\tpublic void remove" + beanName +
                " (" +
                getParamListForRequiredFields((Vector)tablesPkFields.get(posTableRel)) +
                ")\n");
      bc.append("\t\tthrows Exception {\n");
      bc.append("\t\ttry {\n");
      bc.append("\t\t\tContext ctx = new InitialContext();\n");
      bc.append("\t\t\t" + beanName + "Home ejbHome = (" + beanName +
                "Home) ctx.lookup(\"" + EJBName + "." + beanName + "EJB\");\n");
      bc.append("\t\t\t" + beanName + "PK pk = new " + beanName + "PK(" +
      getParamListCallWithRequiredFields((Vector)tablesPkFields.get(posTableRel)) + ");\n");
      bc.append("\t\t\t" + beanName + " ejb = ejbHome.findByPrimaryKey(pk);\n");
      bc.append("\t\t\tejb.remove();\n");
      bc.append("\t\t} catch (Exception e) {\n");
      bc.append("\t\t\t throw new Exception(e.getMessage());\n");
      bc.append("\t\t}\n");
      bc.append("\t}\n\n");
    }
  }

  /**
   * Método para finalizar a classe abstrata, inserindo o métodos requeridos
   * pela especificação EJB 2.0
   * @param bc Classe abstrata
   */
  private void endBeanClass (StringBuffer bc) {
    bc.append("\tpublic void setEntityContext (EntityContext ctx) {\n");
    bc.append("\t\tthis.ctx = ctx;\n");
    bc.append("\t}\n\n");
    bc.append("\tpublic void unsetEntityContext() {\n");
    bc.append("\t\tthis.ctx = null;\n");
    bc.append("\t}\n\n");
    bc.append("\tpublic void ejbLoad() { }\n\n");
    bc.append("\tpublic void ejbStore() { }\n\n");
    bc.append("\tpublic void ejbActivate() { }\n\n");
    bc.append("\tpublic void ejbPassivate() { }\n\n");
    bc.append("\tpublic void ejbRemove() { }\n\n");
    bc.append("}");
  }

  /**
   * Método para escrever a classe abstrata, criando o caminho de deiretório
   * de acordo com o pacote a partir do diretório selecionado
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   * @param className Nome do bean
   * @param str Classe abstrata
   */
  private void writeBeanClass (String dir, String pacote, String className,
                               String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + pacote + File.separatorChar +
          firstLetterToLowerCase(className);
    String fileName = className + "Bean.java";
    addLog("Gerando a classe asbtratata: " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Método para inicializar a interface Home, inserindo a definição de pacote,
   * imports e assinatura da interface
   * @param beanName Nome do bean
   * @param packageName pacote
   * @param hi Interface Home
   */
  private void beginHomeInterface (String beanName, String packageName, StringBuffer hi) {
    hi.append("package " + packageName + "." + firstLetterToLowerCase(beanName) + ";\n\n");
    hi.append("import java.util.*;\n");
    hi.append("import java.rmi.RemoteException;\n");
    hi.append("import javax.ejb.*;\n\n");
    hi.append("public interface " + beanName + "Home extends EJBHome {\n\n");
  }

  /**
   * Método para adicionar o método create, tendo como parâmetro todos os campos
   * requeridos
   * @param beanName Nome do bean
   * @param fields Campos do bean
   * @param hi Interface Home
   */
  private void addCreateMethodToHomeInterface (String beanName, Vector fields, StringBuffer hi) {
    String params = this.getParamListForRequiredFields(fields);
    hi.append("\tpublic " + beanName + " create (" + params + ")\n");
    hi.append("\t\tthrows CreateException, RemoteException;\n\n");
  }

  /**
   * Método para inserir os métodos finders (findByPrimaryKey e findAll)
   * na interface home.
   * @param Nome do bean
   * @param hi Interface Home
   */
  private void addFindersToHomeInterface (String tableName, StringBuffer hi,
                                          Vector fields, Vector uniqueFields) {
    hi.append("\tpublic " + tableName + " findByPrimaryKey (" + tableName + "PK pk)\n");
    hi.append("\t\tthrows FinderException, RemoteException;\n\n");
    hi.append("\tpublic Collection findAll ()\n");
    hi.append("\t\tthrows FinderException, RemoteException;\n\n");
    for (int i = 0; i < uniqueFields.size(); i++) {
      String fieldName = (String) uniqueFields.get(i);
      Field fieldAux = new Field(0, fieldName);
      int index = fields.indexOf(fieldAux);
      Field field = (Field) fields.get(index);
      hi.append("\tpublic " + tableName + " findBy" + firstLetterToUpperCase(field.name) +
                " (" + field.typeName + " value)\n");
      hi.append("\t\tthrows FinderException, RemoteException;\n\n");
    }
  }

  /**
   * Método para finalizar a interface home.
   * @param hi Interface home
   **/
  private void endHomeInterface (StringBuffer hi) {
    hi.append("}");
  }

  /**
   * Método para escrever o arquivo da interface home
   * @param dir diretório
   * @param pacote Pacote
   * @param className Nome da Classe
   * @param str Interface home
   */
  private void writeHomeInterface (String dir, String pacote, String className,
                                   String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + pacote + File.separatorChar +
          firstLetterToLowerCase(className);
    String fileName = className + "Home.java";
    addLog("Gerando a Interface Home: " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Método para iniciar a Interface Remota, colocando as definições de package,
   * imports e a assinatura da interface.
   * @param beanName Nome do EnityBean
   * @param packageName nome do pacote
   * @param bc Classe abstrata a ser gerada
   */
  private void beginRemoteInterface (String beanName, String packageName,
                                     Vector rels1, Vector relsN,
                                     StringBuffer ri) {
    ri.append("package " + packageName + "." + firstLetterToLowerCase(beanName) + ";\n\n");
    ri.append("import java.util.*;\n");
    ri.append("import javax.ejb.*;\n");
    ri.append("import java.rmi.RemoteException;\n");
    /* Importando as classes que serão utilizadas nos métodos de negócios */
    for (int i = 0; i < rels1.size(); i++) {
      String tableName = (String) rels1.get(i);
      ri.append("import " + packageName + "." +
                 firstLetterToLowerCase(tableName) +
                 "." + tableName +";\n");
    }
    for (int i = 0; i < relsN.size(); i++) {
      String tableName = (String) relsN.get(i);
      ri.append("import " + packageName + "." +
                 firstLetterToLowerCase(tableName) +
                 "." + tableName +";\n");
    }
    ri.append("\npublic interface " + beanName + " extends EJBObject {\n\n");
  }

  /**
   * Método para inserir os métodos set e get para os atributos CMP na interface remota
   * @param ri Interface remota
   * @param fields Vector com a realação de campos
   * @see br.com.easynet.ide.Field
   */
  private void addFieldsToRemoteInterface (StringBuffer ri, Vector fields) {
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      addFieldToRemoteInterface(ri, field.name, field.typeName);
    }
  }

  /**
   * Método para inserir o par de métodos set/get para um atributo
   * @param ri Interface remota
   * @param fn Nome do atributo
   * @param ft Tipo do atributo
   */
  private void addFieldToRemoteInterface (StringBuffer ri, String fn, String ft) {
    fn = firstLetterToUpperCase(fn);
    ri.append("\tpublic abstract void set" + fn + " (" + ft + " value) throws RemoteException;\n");
    ri.append("\tpublic abstract " + ft + " get" + fn + " () throws RemoteException;\n\n");
  }

  /**
   * Método para inserir a assinatura dos métodos de negócios para fornecer
   * à camada cliente a
   * possibilidade de inserir, recuperar e apagar EntityBeans que tenham
   * relacionamentos N.
   * Serão inserindo 3 métodos chamados de:<br>
   * create[Nome do Bean](campos requeridos),
   * getFromRelationShip[Nome do Bean](),
   * remove[Nome do Bean](campos da chave primária).
   * @param tables Bean com relacionamento N
   * @param fields Vector com os campos (br.com.easynet.ide.Field) de todas as tabelas
   * @param pkFields Vector com os campos (br.com.easynet.ide.Field) chaves de todas as tabelas
   * @param bc Classe Abstrata do Bean
   * @see br.com.easynet.ide.Field
   */
  private void addRelationsNBusinessMethodsToRemoteInterface(Vector tables,
                                                             Vector tablesRels,
                                                             Vector tablesFields,
                                                             Vector tablesPkFields,
                                                             StringBuffer sb) {
    for (int i = 0; i < tablesRels.size(); i++) {
      String beanName = (String) tablesRels.get(i);
      int posTableRel = tables.indexOf(beanName);
      /* Método create */
      sb.append("\tpublic " + beanName + " create" + beanName +
                "(" +
                getParamListForRequiredFields((Vector)tablesFields.get(posTableRel)) +
                ")\n");
      sb.append("\t\tthrows Exception, RemoteException;\n\n");
      /* Método getFromRelationShip */
      sb.append("\tpublic Set getFromRelationShip" + beanName + " () \n");
      sb.append("\t\tthrows RemoteException;\n\n");
      /* Método remove */
      sb.append("\tpublic void remove" + beanName +
                " (" +
                getParamListForRequiredFields((Vector)tablesPkFields.get(posTableRel)) +
                ")\n");
      sb.append("\t\tthrows Exception, RemoteException;\n\n");
    }
  }

  /**
   * Método para inserir a asinatura dos métodos de negócios para fornecer à camada cliente a
   * possibilidade de recuperar EntityBeans que tenham relacionamentos 1. Pois
   * os métodos set e get para os relacionamentos não podem ser oferdados para a
   * camada cliente
   * O método terá o seguinte nome: getFromRelation[Nome do Bean relacionado] e
   * irá retorna a interface remota do bean relacionado, através do método get
   * do próprio relacionamento.
   * @param tables Vector com as tabelas relacionadas
   * @param bc Classe abstrata do Bean
   */
  private void addRelations1BusinessMethodsToRemoteInterface (Vector tables, StringBuffer bc) {
    Iterator i = tables.iterator();
    while (i.hasNext()) {
      String tableName = (String) i.next();
      bc.append("\tpublic " + tableName + " getFromRelation" + tableName + "()\n");
      bc.append("\t\t\tthrows FinderException, RemoteException;\n\n");
    }
  }

  private void addToXmlToRemoteInterface ( StringBuffer ri) {
    ri.append("\tpublic String toXml() throws RemoteException;\n\n");
  }

  /**
   * Método para finalizar a interface remota
   * @param ri interface remota
   */
  private void endRemoteInterface (StringBuffer ri) {
    ri.append("}");
  }

  /**
   * Método para escrever a interface remota, criando o caminho de deretório
   * de acordo com o pacote a partir do diretório selecionado
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   * @param className Nome do bean
   * @param str Interface remota
   */
  private void writeRemoteInterface (String dir, String pacote, String className,
                                     String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + pacote + File.separatorChar +
          firstLetterToLowerCase(className);
    String fileName = className + ".java";
    addLog("Gerando a interface remota : " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Método para iniciar a Classe Chave Primária, colocando as definições de package,
   * imports e a assinatura da classe.
   * @param beanName Nome do EnityBean
   * @param packageName nome do pacote
   * @param pkc Classe Chave Primária a ser gerada
   */
  private void beginPrimaryKeyClass (String beanName, String packageName, StringBuffer pkc) {
    pkc.append("package " + packageName + "." + firstLetterToLowerCase(beanName) + ";\n\n");
    pkc.append("import java.io.Serializable;\n\n");
    pkc.append("public class " + beanName + "PK implements Serializable {\n\n");
  }

  /**
   * Método para inserir os atributos na Classe Chave Primária
   * @param ri Interface remota
   * @param fields Vector com a realação de campos
   * @see br.com.easynet.ide.Field
   */
  private void addFieldsToPrimaryKeyClass (StringBuffer pkc, Vector pkFields) {
    for (int i = 0; i < pkFields.size(); i++) {
      Field field = (Field) pkFields.get(i);
      String fieldDeclaration =
      "\tpublic " + field.typeName + " " + field.name + ";\n";
      pkc.append(fieldDeclaration);
    }
  }

  /**
   * Método para inserir os construtores: padrão e com todos os atributos na Classe Chave Primária
   * @param pkc Classe Chave Primária
   * @param pkFields Campos que formam a chave primária
   */
  private void addConstructoresToPrimaryKeyClass (String beanName, StringBuffer pkc, Vector pkFields) {
    pkc.append("\n\tpublic " + beanName + "PK () {\n");
    pkc.append("\t}\n\n");
    pkc.append("\tpublic " + beanName + "PK (" + this.getParamListForRequiredFields(pkFields) + ") {\n");
    for (int i = 0; i < pkFields.size(); i++) {
      Field field = (Field) pkFields.get(i);
      pkc.append("\t\tthis." + field.name + " = " + field.name + ";\n");
    }
    pkc.append("\t}\n\n");
  }

  /**
   * Método para indica se o tipo do type JDBC corresponde a tipo de classe Java,
   * caso sejo um tipo primitivo (int, long, double, ...) o retorno será falso
   * @param dataType tipo do campo (java.sql.Types)
   * @return true se o campo for representado por uma classe e false se for por um tipo primitivo
   */
  private boolean isClassField (short dataType) {
    boolean resp = true;
    switch (dataType) {
      case java.sql.Types.DOUBLE :
      case java.sql.Types.FLOAT :
      case java.sql.Types.INTEGER :
      case java.sql.Types.NUMERIC :
      case java.sql.Types.REAL :
      case java.sql.Types.SMALLINT :
      case java.sql.Types.TINYINT :
        resp = false;
    }
    return resp;
  }

  /**
   * Método para inserir o método equals na Classe da Chave Primária
   * @param beanName nome do bean
   * @param pkc Classe da Chave Primária
   * @param pkFields Campos da chve primária
   */
  private void addEqualsToPrimaryKeyClass(String beanName, StringBuffer pkc, Vector pkFields) {
    pkc.append("\tpublic boolean equals (Object obj) {\n");
    pkc.append("\t\tboolean resp = true;\n");
    pkc.append("\t\tif (obj instanceof " + beanName + "PK) {\n");
    pkc.append("\t\t\t" + beanName + "PK pk = (" + beanName + "PK) obj;\n");
    for (int i = 0; i < pkFields.size(); i++) {
      Field field = (Field) pkFields.get(i);
// Não precisa mais, pois todos os campos chaves serão objetos
//      if (isClassField(field.type)) {
        pkc.append("\t\t\tresp = resp && " + field.name + ".equals(pk." + field.name + ");\n");
//      } else {
//        pkc.append("\t\t\tresp = resp && " + field.name + " == pk." + field.name + ";\n");
//      }
    }
    pkc.append("\t\t} else {\n");
    pkc.append("\t\t\tresp = false;\n");
    pkc.append("\t\t}\n");
    pkc.append("\t\treturn resp;\n");
    pkc.append("\t}\n\n");
  }

  /**
   * Método para inserir o método hashCode na Classe da Chave Primária
   * @param beanName nome do bean
   * @param pkc Classe da Chave Primária
   * @param pkFields Campos da chve primária
   */
  private void addHashCodeToPrimaryKeyClass(String beanName, StringBuffer pkc, Vector pkFields) {
    pkc.append("\tpublic int hashCode () {\n");
    pkc.append("\t\tint resp = 0;\n");
    for (int i = 0; i < pkFields.size(); i++) {
      Field field = (Field) pkFields.get(i);
// Não precisa mais, pois todos os campos chaves serão objetos
//      if (isClassField(field.type)) {
        pkc.append("\t\tresp = resp + " + field.name + ".hashCode();\n");
//      } else {
//        pkc.append("\t\tresp = resp + (int) " + field.name + ";\n");
//      }
    }
    pkc.append("\t\treturn resp;\n");
    pkc.append("\t}\n\n");
  }

  /**
   * Método para finalizar a Classe Chave Primária
   * @param ri interface remota
   */
  private void endPrimaryKeyClass (StringBuffer pkc) {
    pkc.append("}");
  }

  /**
   * Método para escrever a Classe Chave Primária, criando o caminho de deretório
   * de acordo com o pacote a partir do diretório selecionado
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   * @param className Nome do bean
   * @param str Classe Chave Primária
   */
  private void writePrimaryKeyClass (String dir, String pacote, String className,
                                     String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + pacote + File.separatorChar +
          firstLetterToLowerCase(className);
    String fileName = className + "PK.java";
    addLog("Gerando a classe primária : " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Método para iniciar deploy Descriptor
   * @param beanName Nome do EnityBean
   * @param dir nome do diretório
   * @param packageName nome do pacote
   * @param bc Classe abstrata a ser gerada
   */
  private void beginEjbJar (StringBuffer bc, String displayName) {
    displayName = displayName + "EJB";
    bc.append("<?xml version=\"1.0\"?>\n");
    bc.append("<!DOCTYPE ejb-jar PUBLIC '-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN' 'http://java.sun.com/j2ee/dtds/ejb-jar_2_0.dtd'>\n");
    bc.append("<ejb-jar>\n");
    bc.append("\t<display-name>" + displayName + "</display-name>\n");
  }

  /**
   * Método para inserir a definição de cada EntityBean no arquivo do deploy descriptor
   * ejb-jar.xml
   * @param pacote Pacote
   * @param tables Vector com todas as tabelas
   * @param tablesFields Vector de Vector com os campos de todas as tabelas
   */
  private void addEntitysToEjbJar(String pacote, Vector tables,
                                  Vector tablesFields,
                                  Vector tablesUniqueFields,
                                  StringBuffer jar ) {
    jar.append("\t<enterprise-beans>\n");
    for (int i = 0; i < tables.size(); i++) {
      String tableName = (String) tables.get(i);
      jar.append("\t\t<entity>\n");
      jar.append("\t\t\t<ejb-name>" + tableName + "EJB</ejb-name>\n");
      jar.append("\t\t\t<home>" + pacote + "." + firstLetterToLowerCase(tableName) + "." + tableName + "Home</home>\n");
      jar.append("\t\t\t<remote>" + pacote + "." + firstLetterToLowerCase(tableName) + "." + tableName + "</remote>\n");
      jar.append("\t\t\t<ejb-class>" + pacote + "." + firstLetterToLowerCase(tableName) + "." + tableName + "Bean</ejb-class>\n");
      jar.append("\t\t\t<persistence-type>Container</persistence-type>\n");
      jar.append("\t\t\t<prim-key-class>" + pacote + "." + firstLetterToLowerCase(tableName) + "." + tableName + "PK</prim-key-class>\n");
      jar.append("\t\t\t<reentrant>False</reentrant>\n");
      jar.append("\t\t\t<cmp-version>2.x</cmp-version>\n");
      jar.append("\t\t\t<abstract-schema-name>" + tableName + "EJB</abstract-schema-name>\n");
      Vector fields = (Vector) tablesFields.get(i);
      for (int j = 0; j < fields.size(); j++) {
        Field field = (Field) fields.get(j);
        jar.append("\t\t\t<cmp-field>\n");
        jar.append("\t\t\t\t<field-name>" + field.name + "</field-name>\n");
        jar.append("\t\t\t</cmp-field>\n");
      }
      jar.append("\t\t\t<query>\n");
      jar.append("\t\t\t\t<query-method>\n");
      jar.append("\t\t\t\t\t<method-name>findAll</method-name>\n");
      jar.append("\t\t\t\t\t<method-params/>\n");
      jar.append("\t\t\t\t</query-method>\n");
      jar.append("\t\t\t\t<ejb-ql>\n");
      jar.append("\t\t\t\t\t<![CDATA[SELECT OBJECT(o) FROM " + tableName + "EJB AS o]]>\n");
      jar.append("\t\t\t\t</ejb-ql>\n");
      jar.append("\t\t\t</query>\n");
      Vector uniqueFields = (Vector) tablesUniqueFields.get(i);
      for (int j = 0; j < uniqueFields.size(); j++) {
        String fieldName = (String) uniqueFields.get(j);
        Field fieldAux = new Field(0, fieldName);
        int index = fields.indexOf(fieldAux);
        Field field = (Field) fields.get(index);
        jar.append("\t\t\t<query>\n");
        jar.append("\t\t\t\t<query-method>\n");
        jar.append("\t\t\t\t\t<method-name>" +  "findBy" + firstLetterToUpperCase(field.name) +
                             "</method-name>\n");
        jar.append("\t\t\t\t\t<method-params>\n");
        jar.append("\t\t\t\t\t\t<method-param>" + field.typeName + "</method-param>\n");
        jar.append("\t\t\t\t\t</method-params>\n");
        jar.append("\t\t\t\t</query-method>\n");
        jar.append("\t\t\t\t<ejb-ql>\n");
        jar.append("\t\t\t\t\t<![CDATA[SELECT OBJECT(o) FROM " + tableName + "EJB AS o " +
                              "WHERE o." + field.name + " = ?1 ]]>\n");
        jar.append("\t\t\t\t</ejb-ql>\n");
        jar.append("\t\t\t</query>\n");
      }
      jar.append("\t\t</entity>\n");
    }
    jar.append("\t</enterprise-beans>\n");
  }

  /**
   * Método para inserir os relacionamentos no deploy descriptor: ejb-jar.xml
   */
  private void addRelationsToEjbJar (Vector tables, Vector rels1, Vector relsN,
                                     StringBuffer jar) {
    jar.append("\t<relationships>\n");
    for (int i = 0; i < relsN.size(); i++) {
      /* Tabela com relacionamento 1 */
      String table = (String) tables.get(i);
      /* Tabelas com relacionamentos N */
      Vector relN = (Vector) relsN.get(i);
      for (int j = 0; j < relN.size(); j++) {
        /* Tabela com relacionamento N */
        String tableRel = (String) relN.get(j);
        jar.append("\t\t<ejb-relation>\n");
        jar.append("\t\t\t<ejb-relation-name>" + table + "-" + tableRel + "</ejb-relation-name>\n");
        jar.append("\t\t\t<ejb-relationship-role>\n");
        jar.append("\t\t\t\t<ejb-relationship-role-name>" +
                   firstLetterToLowerCase(table) +
                   "</ejb-relationship-role-name>\n");
        jar.append("\t\t\t\t<multiplicity>one</multiplicity>\n");
        jar.append("\t\t\t\t<relationship-role-source>\n");
        jar.append("\t\t\t\t\t<ejb-name>" + table + "EJB</ejb-name>\n");
        jar.append("\t\t\t\t</relationship-role-source>\n");
        jar.append("\t\t\t\t<cmr-field>\n");
        jar.append("\t\t\t\t\t<cmr-field-name>" +
                   firstLetterToLowerCase(tableRel) +
                   "</cmr-field-name>\n");
        jar.append("\t\t\t\t\t<cmr-field-type>java.util.Set</cmr-field-type>\n");
        jar.append("\t\t\t\t</cmr-field>\n");
        jar.append("\t\t\t</ejb-relationship-role>\n");
        jar.append("\t\t\t<ejb-relationship-role>\n");
        jar.append("\t\t\t\t<ejb-relationship-role-name>" +
                   firstLetterToLowerCase(tableRel) +
                   "</ejb-relationship-role-name>\n");
        jar.append("\t\t\t\t<multiplicity>many</multiplicity>\n");
        jar.append("\t\t\t\t<relationship-role-source>\n");
        jar.append("\t\t\t\t\t<ejb-name>" + tableRel + "EJB</ejb-name>\n");
        jar.append("\t\t\t\t</relationship-role-source>\n");
        jar.append("\t\t\t\t<cmr-field>\n");
        jar.append("\t\t\t\t\t<cmr-field-name>" + firstLetterToLowerCase(table) +
                   "</cmr-field-name>\n");
        jar.append("\t\t\t\t</cmr-field>\n");
        jar.append("\t\t\t</ejb-relationship-role>\n");
        jar.append("\t\t</ejb-relation>\n");
      }
    }
    jar.append("\t</relationships>\n");
  }


  private void endEjbJar (Vector tables, StringBuffer jar) {
    jar.append("\t<assembly-descriptor>\n");
    for (int i = 0; i < tables.size(); i++) {
      String table = (String) tables.get(i);
      jar.append("\t\t<container-transaction>\n");
      jar.append("\t\t\t<method>\n");
      jar.append("\t\t\t\t<ejb-name>" + table + "EJB</ejb-name>\n");
      jar.append("\t\t\t\t<method-name>*</method-name>\n");
      jar.append("\t\t\t</method>\n");
      jar.append("\t\t\t<trans-attribute>Required</trans-attribute>\n");
      jar.append("\t\t</container-transaction>\n");
    }
    jar.append("\t</assembly-descriptor>\n");
    jar.append("</ejb-jar>\n");
  }

  /**
   * Método para escrever o deploy desciptor (ejb-jar.xml)
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   */
  private void writeEjbJar (String dir, String pacote, String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + "meta-inf";
    String fileName = "ejb-jar.xml";
    addLog("Gerando o deploy Descriptor : " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Méotodo para gerar o deployer descriptor: weblogic-ejb-jar.xml
   * @param tables Vector com o nome de todas as tabelas
   * @param dd Deploy Descriptor: weblogic-ejb-jar.xml
   */
  private void beginWebLogigEjbJar (Vector tables, String ejbName, StringBuffer dd) {
    dd.append("<?xml version=\"1.0\"?>\n");
    dd.append("<!DOCTYPE weblogic-ejb-jar PUBLIC '-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB//EN' 'http://www.bea.com/servers/wls600/dtd/weblogic-ejb-jar.dtd'>\n");
    dd.append("<weblogic-ejb-jar>\n");
    for (int i = 0; i < tables.size(); i++) {
      String table = (String) tables.get(i);
      dd.append("\t<weblogic-enterprise-bean>\n");
      dd.append("\t\t<ejb-name>" + table + "EJB</ejb-name>\n");
      dd.append("\t\t<entity-descriptor>\n");
      dd.append("\t\t\t<entity-cache>\n");
      dd.append("\t\t\t\t<max-beans-in-cache>20</max-beans-in-cache>\n");
      dd.append("\t\t\t\t<read-timeout-seconds>600</read-timeout-seconds>\n");
      dd.append("\t\t\t\t<concurrency-strategy>Database</concurrency-strategy>\n");
      dd.append("\t\t\t</entity-cache>\n");
      dd.append("\t\t\t<lifecycle>\n");
      dd.append("\t\t\t\t<passivation-strategy>Default</passivation-strategy>\n");
      dd.append("\t\t\t</lifecycle>\n");
      dd.append("\t\t\t<persistence>\n");
      dd.append("\t\t\t\t<persistence-type>\n");
      dd.append("\t\t\t\t\t<type-identifier>WebLogic_CMP_RDBMS</type-identifier>\n");
      dd.append("\t\t\t\t\t<type-version>6.0</type-version>\n");
      dd.append("\t\t\t\t\t<type-storage>META-INF/weblogic-cmp-rdbms-jar.xml</type-storage>\n");
      dd.append("\t\t\t\t</persistence-type>\n");
      dd.append("\t\t\t\t\t<db-is-shared>True</db-is-shared>\n");
      dd.append("\t\t\t\t<persistence-use>\n");
      dd.append("\t\t\t\t\t<type-identifier>WebLogic_CMP_RDBMS</type-identifier>\n");
      dd.append("\t\t\t\t\t<type-version>6.0</type-version>\n");
      dd.append("\t\t\t\t</persistence-use>\n");
      dd.append("\t\t\t</persistence>\n");
      dd.append("\t\t</entity-descriptor>\n");
      dd.append("\t\t<enable-call-by-reference>True</enable-call-by-reference>\n");
      dd.append("\t\t<jndi-name>" + firstLetterToLowerCase(ejbName) + "." + table + "EJB</jndi-name>\n");
      dd.append("\t</weblogic-enterprise-bean>\n");
    }
    dd.append("</weblogic-ejb-jar>");
  }

  /**
   * Método para escrever o deploy desciptor (weblogic-ejb-jar.xml)
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   */
  private void writeWeblogicEjbJar (String dir, String pacote, String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + "meta-inf";
    String fileName = "weblogic-ejb-jar.xml";
    addLog("Gerando o deploy Descriptor : " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }


  private void addRelationsToWeblogicCmpRdbmsJar (DatabaseMetaData dbmd, StringBuffer dd) {
		String[] tableTypes = {"TABLE"};
    try {
      ResultSet tables = dbmd.getTables(null, null, "%", tableTypes );
      while (tables.next()) {
        String tableName = tables.getString("TABLE_NAME");
        ResultSet exportedKeys = dbmd.getExportedKeys(null, null, tableName);
        String fkTableNameAux = "";
        boolean hasExportedKeys = false;
        while (exportedKeys.next()) {
          hasExportedKeys = true;
          String pkTableName  = exportedKeys.getString("PKTABLE_NAME");
          String pkColumnName = firstLetterToLowerCase(exportedKeys.getString("PKCOLUMN_NAME"));
          String fkTableName  = exportedKeys.getString("FKTABLE_NAME");
          String fkColumnName = firstLetterToLowerCase(exportedKeys.getString("FKCOLUMN_NAME"));
          String fkKeySeq     = exportedKeys.getString("KEY_SEQ");

          if (fkTableNameAux.equals(fkTableName)) {
            dd.append("\t\t\t<column-map>\n");
            dd.append("\t\t\t\t<foreign-key-column>" + fkColumnName + "</foreign-key-column>\n");
            dd.append("\t\t\t\t<key-column>" + pkColumnName + "</key-column>\n");
            dd.append("\t\t\t</column-map>\n");
          } else if (fkTableNameAux.equals("")) {
            dd.append("\t<weblogic-rdbms-relation>\n");
            dd.append("\t\t<relation-name>" + pkTableName + "-" + fkTableName +"</relation-name>\n");
            dd.append("\t\t<weblogic-relationship-role>\n");
            dd.append("\t\t\t<relationship-role-name>" + firstLetterToLowerCase(fkTableName) + "</relationship-role-name>\n");
            dd.append("\t\t\t<column-map>\n");
            dd.append("\t\t\t\t<foreign-key-column>" + fkColumnName + "</foreign-key-column>\n");
            dd.append("\t\t\t\t<key-column>" + pkColumnName + "</key-column>\n");
            dd.append("\t\t\t</column-map>\n");
          } else if(!fkTableNameAux.equals(fkTableName)) {
            dd.append("\t\t</weblogic-relationship-role>\n");
            dd.append("\t</weblogic-rdbms-relation>\n");

            dd.append("\t<weblogic-rdbms-relation>\n");
            dd.append("\t\t<relation-name>" + pkTableName + "-" + fkTableName +"</relation-name>\n");
            dd.append("\t\t<weblogic-relationship-role>\n");
            dd.append("\t\t\t<relationship-role-name>" + firstLetterToLowerCase(fkTableName) + "</relationship-role-name>\n");
            dd.append("\t\t\t<column-map>\n");
            dd.append("\t\t\t\t<foreign-key-column>" + fkColumnName + "</foreign-key-column>\n");
            dd.append("\t\t\t\t<key-column>" + pkColumnName + "</key-column>\n");
            dd.append("\t\t\t</column-map>\n");
          }
          fkTableNameAux = fkTableName;
        }
        if (hasExportedKeys) {
            dd.append("\t\t</weblogic-relationship-role>\n");
            dd.append("\t</weblogic-rdbms-relation>\n");
        }
      }
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Méotodo para gerar o deployer descriptor: weblogic-cmp-rdbms-jar.xml
   * @param tables Vector com o nome de todas as tabelas
   * @param dd Deploy Descriptor: weblogic-ejb-jar.xml
   */
  private void beginWeblogicCmpRdbmsJar (DatabaseMetaData dbmd,
                                         Vector tables,
                                         Vector tablesFields,
                                         String dataSource,
                                         StringBuffer dd) {
    dd.append("<?xml version=\"1.0\"?>\n");
    dd.append("<!DOCTYPE weblogic-rdbms-jar PUBLIC '-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN' 'http://www.bea.com/servers/wls600/dtd/weblogic-rdbms20-persistence-600.dtd'>\n");
    dd.append("<weblogic-rdbms-jar>\n");
    /* Inserindo a definição dos Beans */
    for (int i = 0; i < tables.size(); i++) {
      String table = (String) tables.get(i);
      dd.append("\t<weblogic-rdbms-bean>\n");
      dd.append("\t\t<ejb-name>" + table + "EJB</ejb-name>\n");
      dd.append("\t\t<data-source-name>" + dataSource + "</data-source-name>\n");
      dd.append("\t\t<table-name>" + table + "</table-name>\n");
      Vector fields = (Vector) tablesFields.get(i);
      for (int j = 0; j < fields.size(); j++) {
        Field field = (Field) fields.get(j);
        dd.append("\t\t<field-map>\n");
        dd.append("\t\t\t<cmp-field>" +
                   firstLetterToLowerCase(field.name) +
                  "</cmp-field>\n");
        dd.append("\t\t\t<dbms-column>" + field.name + "</dbms-column>\n");
        dd.append("\t\t</field-map>\n");
      }
      dd.append("\t</weblogic-rdbms-bean>\n");
    }
    /* Inserindo os relacionamentos */
    addRelationsToWeblogicCmpRdbmsJar(dbmd,dd);
    dd.append("</weblogic-rdbms-jar>\n");
  }


  /**
   * Método para escrever o deploy desciptor (weblogic-cmp-rdbms-jar.xml)
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   */
  private void writeWeblogicCmlRdbmsJar (String dir, String pacote, String str) {
    pacote = pacote.replace('.', File.separatorChar);
    dir = dir + File.separatorChar + "meta-inf";
    String fileName = "weblogic-cmp-rdbms-jar.xml";
    addLog("Gerando o deploy Descriptor : " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   *
   */
   private void beginBuildXml(Vector tables, String pacote, String ejbName, StringBuffer sb) {
    sb.append("<project name=\"" + ejbName  + "EJB" + "\" default=\"all\" basedir=\".\">\n");

    sb.append("\t<property name=\"JAVAC\" value=\"modern\"/>\n");
    sb.append("\t<property name=\"WL_HOME\" value=\"" + jTextFieldWL_HOME.getText() + "\"/>\n");
    sb.append("\t<property name=\"source\" value=\".\"/>\n");
    sb.append("\t<property name=\"javasources\" value=\"${source}/..\"/>\n");
    sb.append("\t<property name=\"build\" value=\"${source}/../build\"/>\n");
    sb.append("\t<property name=\"dist\" value=\"${source}/../dist\"/>\n");

    sb.append("\t<target name=\"all\" depends=\"clean, init, compile_ejb, jar_ejb, ejbc\"/>\n");

    sb.append("\t<target name=\"init\">\n");
    sb.append("\t\t<tstamp/>\n");
    sb.append("\t\t<mkdir dir=\"${build}\"/>\n");
    sb.append("\t\t<mkdir dir=\"${build}/META-INF\"/>\n");
    sb.append("\t\t<mkdir dir=\"${dist}\"/>\n");
    sb.append("\t\t<copy todir=\"${build}/META-INF\">\n");
    sb.append("\t\t\t<fileset dir=\"${source}\">\n");
    sb.append("\t\t\t\t<include name=\"*.xml\"/>\n");
    sb.append("\t\t\t\t<exclude name=\"build.xml\"/>\n");
    sb.append("\t\t\t</fileset>\n");
    sb.append("\t\t</copy>\n");
    sb.append("\t</target>\n");

    sb.append("\t<target name=\"compile_ejb\">\n");
    sb.append("\t\t<javac srcdir=\"${javasources}\" destdir=\"${build}\"\n");
    /* Obtendo o nome das classes */
    StringBuffer classes = new StringBuffer();
    pacote = pacote.replace('.','/');
    for (int i = 0; i < tables.size(); i++) {
      String tableName = (String) tables.get(i);
      classes.append("\t\t\t\t\t" + pacote + "/" + firstLetterToLowerCase(tableName) + "/" + tableName + ".java,\n");
      classes.append("\t\t\t\t\t" + pacote + "/" + firstLetterToLowerCase(tableName) + "/" + tableName + "Home.java,\n");
      classes.append("\t\t\t\t\t" + pacote + "/" + firstLetterToLowerCase(tableName) + "/" + tableName + "Bean.java,\n");
      classes.append("\t\t\t\t\t" + pacote + "/" + firstLetterToLowerCase(tableName) + "/" + tableName + "PK.java,\n\n");
    }
    /* Removendo a última vírgula */
    classes.delete(classes.length()-3, classes.length());
    sb.append("\t\t\tincludes=\"\n" + classes.toString() + "\n");
    sb.append("\t\t\"/>\n");
    sb.append("\t</target>\n");
    sb.append("\t<target name=\"jar_ejb\" depends=\"compile_ejb\">\n");
    sb.append("\t\t<jar jarfile=\"${dist}/" + ejbName + "EJBClient.jar\"\n");
    sb.append("\t\t\tbasedir=\"${build}\">\n");
    sb.append("\t\t</jar>\n");
    sb.append("\t</target>\n");

    sb.append("\t<target name=\"ejbc\" depends=\"jar_ejb\">\n");
    sb.append("\t\t<java classname=\"weblogic.ejbc\" fork=\"yes\">\n");
    sb.append("\t\t\t<sysproperty key=\"weblogic.home\" value=\"${WL_HOME}\"/>\n");
    sb.append("\t\t\t<arg line=\"-compiler javac ${dist}/" + ejbName + "EJBClient.jar ${dist}/" + ejbName + "EJBServer.jar\"/>\n");
    sb.append("\t\t\t<classpath>\n");
    sb.append("\t\t\t\t<pathelement path=\"${WL_HOME}/lib/weblogic_sp.jar;${WL_HOME}/lib/weblogic.jar\"/>\n");
    sb.append("\t\t\t</classpath>\n");
    sb.append("\t\t</java>\n");
    sb.append("\t</target>\n");

    sb.append("\t<target name=\"clean\">\n");
    sb.append("\t\t<delete dir=\"${build}\"/>\n");
    sb.append("\t\t<delete dir=\"${dist}\"/>\n");
    sb.append("\t</target>\n");

    sb.append("</project>");
   }

  /**
   * Método para escrever o arquivo build.xml
   * @param dir diretório raíz, onde os pacotes serão criados
   * @param pacote Pacote para as classes
   */
  private void writeBuildXml (String dir, String str) {
    dir = dir + File.separatorChar + "meta-inf";
    String fileName = "build.xml";
    addLog("Gerando : " + fileName);
    try {
      writeTextFile(dir, fileName, str);
    } catch (Exception e) {
      addLog(e.getMessage());
    }
  }

  /**
   * Método para escrever um arquivo texto a partir de uma String
   * @param dir Caminho de diretório, o qual será criado caso não exista
   * @param fileName Nome do arquivo
   * @param Conteúdo do arquivo
   */
  private void writeTextFile (String dir, String fileName, String str)
    throws Exception{
    try {
      File dirFile = new File(dir);
      dirFile.mkdirs();
      File file = new File(dir + File.separatorChar + fileName);
      FileWriter fw	= new FileWriter(file);
      fw.write(str);
      fw.flush();
      fw.close();
    } catch (Exception e) {
      throw new Exception ("Erro escrevendo o arquivo" + e.getMessage());

    }
  }

  /**
   * Método para retornar o tipo java adequado para o tipo JDBC fornecido
   * @param dataType tipo (java.sql.Types)
   * @result Tipo java compatível com dataType fornecido
   */
  private String getDataTypeName (short dataType) throws Exception {
    String dataTypeName = "";
    switch (dataType) {
      case java.sql.Types.CHAR:
        dataTypeName = "java.lang.String"; break;
      case java.sql.Types.VARCHAR:
        dataTypeName = "java.lang.String"; break;
      case java.sql.Types.INTEGER:
        dataTypeName = "java.math.BigDecimal"; break;
      case java.sql.Types.NUMERIC:
        dataTypeName = "java.math.BigDecimal"; break;
      case java.sql.Types.FLOAT:
        dataTypeName = "java.math.BigDecimal"; break;
      case java.sql.Types.DOUBLE:
        dataTypeName = "java.math.BigDecimal"; break;
      case java.sql.Types.DATE:
        dataTypeName = "java.sql.Date"; break;
      case java.sql.Types.TIME:
        dataTypeName = "java.sql.Time"; break;
      case java.sql.Types.TIMESTAMP:
        dataTypeName = "java.sql.Timestamp"; break;
      default :
        throw new Exception("Tipo não tratado. Método:" +
        "br.com.easynet.ide.EntityBeanGenerator.getDataTypeName(...)");
    }
    return dataTypeName;
  }

  private String firstLetterToLowerCase(String str) {
    String resp = str.substring(0,1).toLowerCase() +
                  str.substring(1);
    return resp;
  }

  private String firstLetterToUpperCase(String str) {
    String resp = str.substring(0,1).toUpperCase() +
                  str.substring(1);
    return resp;
  }

  /**
   * Método para gerar um Vector com informações sobre os campos do ResultSet,
   * o qual foi extraído com o método DataBaseMetada.getColumns(...).
   * @param ResultSet
   * @param fields Vector a ser preenchido com objetos br.com.easynet.ide.Field
   * @see br.com.easynet.ide.Field
   */
  private void processFields (ResultSet rs, Vector fields)
   throws Exception {
    while (rs.next()) {
      String columnName = firstLetterToLowerCase(rs.getString("COLUMN_NAME"));
      short dataType = rs.getShort("DATA_TYPE");
      int ordinalPosition = rs.getShort("ORDINAL_POSITION");
      int nullable = rs.getInt("NULLABLE");
      String dataTypeName = "";
      try {
        dataTypeName = getDataTypeName(dataType);
      } catch (Exception exception) {
        dataTypeName = "String";
      }
      fields.add(new Field(ordinalPosition, columnName, dataType, dataTypeName, nullable));
    }
  }

  /**
   * Método para gerar um Vector com os campos que formam a chave primária a
   * partir do ResultSet fornecido gerado pelo método DataBaseMetada.getPrimaryKeys.<br>
   * Obs: O Vector será ordenado Collections.sort(Vector)
   * @param ResultSet
   * @param fields Campos do bean
   * @param pkFields Vector a ser preenchido com objetos br.com.easynet.ide.Field
   * que formam a chave primária
   * @see br.com.easynet.ide.Field
   */
  private void processPrimaryKey (ResultSet rs, Vector fields, Vector pkFields)
    throws Exception {
    while (rs.next()) {
      String pkColumnName = firstLetterToLowerCase(rs.getString("COLUMN_NAME"));
      short pkSeq = rs.getShort("KEY_SEQ");
      /* Novo campo da chave estrangeira */
      Field pkField = new Field(pkSeq, pkColumnName);
      /* Campo da chave primária extraído da lista de campos */
      Field newPkField = (Field) fields.get(fields.indexOf(pkField));
      /* Alterando a ordem do campo para ser a sua ordem na chaves */
      newPkField.order = pkField.order;
      /*
       * Adicionando o campo à relação dos campos da chave primária,
       * sendo que agora o mesmo já possui a informação do tipo
       */
      pkFields.add(newPkField);
    }
    Collections.sort(pkFields);
  }

  private void processImportedKeys (ResultSet rs, Vector fields, Vector ikFields)
    throws Exception {
    while (rs.next()) {
      String fkColumnName = firstLetterToLowerCase(rs.getString("FKCOLUMN_NAME"));
      short fkKeySeq = rs.getShort("KEY_SEQ");
      Field ikField = new Field(fkKeySeq, fkColumnName);
      Field newIKfield = (Field) fields.get(fields.indexOf(ikField));
      ikFields.add(newIKfield);
    }
  }

  /**
   * Método para ajustar os tipos dos campos que formam a chave primária,
   * pois campos que participam de relacionamentos não podem ser de tipo
   * primitivo
   */
  private void processFieldsKeyType(Vector fields, Vector pkFields,
                                    Vector ikFields) {
    for (int i = 0; i < fields.size(); i++) {
      Field field = (Field) fields.get(i);
      /* Verificando se o campo faz parte da chave primária ou é chave estrangeira*/
      if (pkFields.indexOf(field) != -1 ||
          ikFields.indexOf(field) != -1) {
        /* Verificando se o campo é de um tipo primitivo */
        if (field.typeName.equals("int")) {
          /* Ajustando o tipo */
          field.typeName = "java.lang.String";
          field.type = java.sql.Types.CHAR;
        }
      }
    }
  }

  private void processRelations1 (ResultSet rs, Vector relations1)
    throws Exception {
    while (rs.next()) {
      String pkTableName  = rs.getString("PKTABLE_NAME");
      String pkColumnName = firstLetterToLowerCase(rs.getString("PKCOLUMN_NAME"));
      String fkTableName  = rs.getString("FKTABLE_NAME");
      String fkColumnName = firstLetterToLowerCase(rs.getString("FKCOLUMN_NAME"));
      String fkKeySeq     = rs.getString("KEY_SEQ");
      /* Armazenando o nome da tabela relacionada por uma chave importada */
      if (relations1.indexOf(pkTableName) == -1) {
        relations1.add(pkTableName);
      }
    }
  }

  private void processRelationsN (ResultSet rs, Vector relationsN)
    throws Exception {
    while (rs.next()) {
      String pkTableName  = rs.getString("PKTABLE_NAME");
      String pkColumnName = firstLetterToLowerCase(rs.getString("PKCOLUMN_NAME"));
      String fkTableName  = rs.getString("FKTABLE_NAME");
      String fkColumnName = firstLetterToLowerCase(rs.getString("FKCOLUMN_NAME"));
      String fkKeySeq     = rs.getString("KEY_SEQ");
      /* Armazenando o nome da tabela relacionada por uma chave exportada */
      if (relationsN.indexOf(fkTableName) == -1) {
        relationsN.add(fkTableName);
      }
    }
  }

  /**
   * Método para obter metadados do SBGD e gerar EntityBeans
   * @param connection Conexão com o Banco, o qual será será gerado os Entity Beans
   */
  private void beanGenerate(Connection connection) throws Exception {
			/* Obtendo objeto com metadados do banco de dados */
			DatabaseMetaData dbmd = connection.getMetaData();
			/* Obtendo metadados do banco de dados */
      addLog("SGBD = " + dbmd.getDatabaseProductName());
      addLog("UserName = " + dbmd.getUserName());
			addLog("DriverName = " + dbmd.getDriverName());
			addLog("DriverVersion = " + dbmd.getDriverVersion() );
      /*
       * Para a geração dos métodos que vão fornecer a visão dos relacionamentos
       * à camada cliente (createAndAddToRelationShip , AddToRelationShip ,
       *                   getFromRelationShip e removeFromRelationShip)
       * será necessário possuir a relação de campos requeridos e os que foram a
       * chave primária para cada tabela, assim serão criados 3 Vectors para
       * armazer: os nomes das tabelas (beans), Vector com os campos requeridos e
       * outro com campos que formam a chave primária, sabendo que a relação entre as tabelas e
       * os Vector que possuem seus campos será feita pela posição nos vetores.
       * Ex:
       * tables=["Tabela1", Tabela2]
       * tablesPkFields=[[campo1,campo2,campo3],[campo1,campo2]]
       * tablesReqFields=[[campo1],[campo1,campo2]]
       * Para o método createAndAddToRelationShip será necessário passar como parametro
       * todos os campos requeridos.
       * Para os métodos getgetFromRelationShip e removeFromRelationShip será
       * nescessario passar como parâmetro os campos que formam a chave primária.
       * Para o método AddToRelationShip será necessário passar um objeto que
       * implemente a interface remota do bean relacionado
       *
       */
      /* Vector para armazer a relação de tabelas do banco de Dados */
      Vector tables = new Vector();
      /* Índice para o Vector com as tabelas, para permitir inserir nos outros
       * vetores na mesma posição da tabelas
       */
      int iTables = 0;
      /* Vector para armazer os Vector com os campos requeridos de cada tabela */
      Vector tablesFields = new Vector();
      /* Vector para armazer os Vector com os campos da chave primária de cada tabela */
      Vector tablesPkFields = new Vector();
      /* Vector para armazer os Vectors com as tabelas relacionadas 1 */
      Vector tablesRelations1 = new Vector();
      /* Vector para armazer os Vectors com as tabelas relacionadas N */
      Vector tablesRelationsN = new Vector();
      /* Vector para armazenar os campos únicos de cada tabela, definidos através de índices */
      Vector tablesUniqueFields = new Vector();
			/* tipos de tabelas a serem obtidas */
			String[] tableTypes = {"TABLE"};
			/* Obtendo todas as tabelas do SGBD */
			ResultSet tablesRS = dbmd.getTables(null, null, "%", tableTypes);
      while (tablesRS.next()) {
        /* Obtendo o nome da tabela */
        String tableName = firstLetterToUpperCase(tablesRS.getString("TABLE_NAME"));
        /* Inserindo no Vector com a relação das tabelas */
        tables.addElement(tableName);
        /* Obtendo as colunas da tabela */
        ResultSet columns = dbmd.getColumns(null,null,tableName,"%");
        /* Coleção de campos (br.com.easynet.ide.Field) da tabela */
        Vector fields = new Vector();
        /* Armazenando a relação de campos (br.com.easynet.ide.Field) da tabela */
        processFields(columns, fields);
        tablesFields.insertElementAt(fields, iTables);
        /* Obtendo os campos que formam a chave primária */
        ResultSet primaryKeys = dbmd.getPrimaryKeys(null, null, tableName);
        /* Campos que formam a chave primária */
        Vector pkFields = new Vector();
        processPrimaryKey(primaryKeys, fields, pkFields);
        tablesPkFields.insertElementAt(pkFields, iTables);
        /* Obtendo as chaves estrangeiras */
        ResultSet foreignKeys = dbmd.getImportedKeys(null, null, tableName);
        /* Tabelas com relacionamentos 1 */
        Vector relations1to1 = new Vector();
        /* Processando as tabelas com relacionamentos 1 */
        processRelations1(foreignKeys, relations1to1);
        tablesRelations1.insertElementAt(relations1to1, iTables);
        /* Obtendo as chaves exportadas */
        ResultSet exportedKeys = dbmd.getExportedKeys(null, null, tableName);
        /* Tabelas com relacionamentos N */
        Vector relations1toN = new Vector();
        /* Processando as tabelas com relacionamentos N */
        processRelationsN(exportedKeys, relations1toN);
        tablesRelationsN.insertElementAt(relations1toN, iTables);
        /* Obtendo os campos que são chave importada */
        ResultSet rs = dbmd.getImportedKeys(null, null, tableName);
        /* Vector com os Fields que são chave importada */
        Vector ikFields = new Vector();
        /* Encontrando os campos que são chaves importadas */
        processImportedKeys(rs, fields, ikFields);

        /*
         * Ajustando os tipos dos campos que formam a chaves (PK ou FK),
         * pois campos que fazem parte de relacionamento nao podem ser de
         * tipo primitivo, assim os campos que formam a chave e for
         * de tipo primitivo (ex. int, float, etc) terão seus tipos alterados
         * para String
         */
/*
 * Esse método não é mais nescessário, pois os campos numéricos serão todos
 * tratados como java.math.BigDecimal, assim não haverá mais campos numéricos
 * de tipo primitivo
 */
//        processFieldsKeyType(fields, pkFields, ikFields);

        /*
         * Obtendo os índices para os campos únicos da tabela,
         * com o objetivo de gerar os métodos findBy[NomeDoCampo] na interface
         * Home e as respectivas querys EJBQL no arquivo ejb-jar.xml
         */
        Vector uniqueFields = new Vector();
        /* Obtendo todos os índices para campos únicos */
        rs = dbmd.getIndexInfo(null, null, tableName, true, false);
        while (rs.next()) {
          String uniqueFieldName = rs.getString("COLUMN_NAME");
          short type = rs.getShort("TYPE");
          if ( type == dbmd.tableIndexOther ) {
            uniqueFields.add(uniqueFieldName);
          }
         }
         tablesUniqueFields.insertElementAt(uniqueFields, iTables);
        iTables++;
      }
      /* Usando os metadas para gerar os compoenentes dos Entity Beans */
      for (int i = 0; i < tables.size(); i++) {
        String tableName = (String) tables.get(i);
        /*
         * Gerando a CLASSE ABSTRATA
         */
        /* Classe abstrata */
        StringBuffer beanClass = new StringBuffer();
        /* Iniciando a classe abstrata */
        beginBeanClass(tableName, jTextFieldPacote.getText(),
                       (Vector) tablesRelations1.get(i),
                       (Vector) tablesRelationsN.get(i),
                       beanClass);
        /* Inserindo os métodos set e get para os atributos */
        addFieldsToBeanClass(beanClass, (Vector) tablesFields.get(i));
        /* Inserindo os métodos set e get para os relacionament 1x1 */
        addRelations1ToBeanClass((Vector) tablesRelations1.get(i), beanClass);
        /* Inserindo os métodos set e get para os relacionament 1xN */
        addRelationsNToBeanClass((Vector) tablesRelationsN.get(i), beanClass);
        /* Inserindo métodos para oferecer operações sobre os relacionamentos à camada cliente */
        addRelations1BusinessMethodsToBeanClass((Vector) tablesRelations1.get(i), beanClass);
        addRelationsNBusinessMethodsToBeanClass(tables,
                                                (Vector) tablesRelationsN.get(i),
                                                tablesFields,
                                                tablesPkFields,
                                                jTextFieldNomeEJB.getText(),
                                                beanClass);
        /* Inserindo o método toXML na classe abstrata */
        addToXmlToBeanClass((Vector) tablesFields.get(i), tableName, beanClass);
        /* Inserindo os métodos ejbCreate e ejbPosCreate na classe abstrata */
        addEjbCreateMethodsToBeanClass(tableName, (Vector) tablesFields.get(i), beanClass);
        /* Finalizando a classe abstrata */
        endBeanClass(beanClass);
        /* Salvando a classe abstrata gerada */
        writeBeanClass(jTextFieldDir.getText(), jTextFieldPacote.getText(),
                       tableName, beanClass.toString());
        /*
         * Gerando a INTERFACE HOME
         */
        /* Interface Home */
        StringBuffer homeInterface = new StringBuffer();
        /* Iniciando a interface Home */
        beginHomeInterface(tableName, jTextFieldPacote.getText(), homeInterface);
        /* Inserindo o método create da interface home */
        addCreateMethodToHomeInterface(tableName,  (Vector) tablesFields.get(i), homeInterface);
        /* Inserindo os métodos finders na Interface Home */
        addFindersToHomeInterface(tableName, homeInterface,
                                  (Vector) tablesFields.get(i),
                                  (Vector) tablesUniqueFields.get(i));
        /* Finalizando a Interface Home */
        endHomeInterface(homeInterface);
        /* Salvando a Interface Home gerada */
        writeHomeInterface(jTextFieldDir.getText(), jTextFieldPacote.getText(),
                           tableName, homeInterface.toString());
        /*
         * Gerando a INTERFACE REMOTA
         */
        /* Interface Remota */
        StringBuffer remoteInterface = new StringBuffer();
        /* Iniciando a interface Remota */
        beginRemoteInterface(tableName, jTextFieldPacote.getText(),
                             (Vector) tablesRelations1.get(i),
                             (Vector) tablesRelationsN.get(i),
                             remoteInterface);
        /* Inserindo os métodos set e get para os atributos */
        addFieldsToRemoteInterface(remoteInterface,  (Vector) tablesFields.get(i));
        /* Inserindo as assinaturas do métodos de negócios para relacionamentos */
        addRelations1BusinessMethodsToRemoteInterface((Vector) tablesRelations1.get(i),
                                                       remoteInterface);
        addRelationsNBusinessMethodsToRemoteInterface(tables,
                                                      (Vector) tablesRelationsN.get(i),
                                                      tablesFields,
                                                      tablesPkFields,
                                                      remoteInterface);;
        /* Inserindo o método toXml() */
        this.addToXmlToRemoteInterface(remoteInterface);

        /* Finalizando a Interface Home */
        endRemoteInterface(remoteInterface);
        /* Salvando a Interface Home gerada */
        writeRemoteInterface(jTextFieldDir.getText(), jTextFieldPacote.getText(),
                             tableName, remoteInterface.toString());
        /*
         * Gerando a Classe Chave Primária
         */
        /* Classe Chave Primária */
        StringBuffer primaryKeyClass = new StringBuffer();
        /* Iniciando Classe Chave Primária */
        beginPrimaryKeyClass(tableName, jTextFieldPacote.getText(), primaryKeyClass);
        /* Inserindo os atributos na Classe Chave Primária */
        addFieldsToPrimaryKeyClass (primaryKeyClass, (Vector) tablesPkFields.get(i));
        /* Inserindo os construtores na Classe Chave Primária */
        addConstructoresToPrimaryKeyClass(tableName, primaryKeyClass,
                                          (Vector) tablesPkFields.get(i));
        /* Inserindo o método equals na Classe da Chave Primária */
        addEqualsToPrimaryKeyClass(tableName, primaryKeyClass,
                                   (Vector) tablesPkFields.get(i));
        /* Inserindo o método hashcode na Classe da Chave Primária */
        addHashCodeToPrimaryKeyClass(tableName, primaryKeyClass,
                                     (Vector) tablesPkFields.get(i));
        /* Finalizando a Classe Chave Primária */
        endPrimaryKeyClass(primaryKeyClass);
        /* Salvando a Classe Chave Primária gerada */
        writePrimaryKeyClass(jTextFieldDir.getText(), jTextFieldPacote.getText(),
                             tableName, primaryKeyClass.toString());
      }
      /*
       * Gerando o Deployer Descriptor ejb-jar.xml
       */
      StringBuffer ejbJar = new StringBuffer("");
      /* Iniciando o Deploy Descriptor*/
      beginEjbJar(ejbJar, jTextFieldNomeEJB.getText());
      /* Inserindo a definição para o Entity Beans no deploy descriptor ejb-jar */
      addEntitysToEjbJar(jTextFieldPacote.getText(), tables,
                         tablesFields, tablesUniqueFields,
                         ejbJar);
      addRelationsToEjbJar(tables, tablesRelations1, tablesRelationsN, ejbJar);
      /* Finalizando o deploy descriptor */
      endEjbJar(tables, ejbJar);
      /* Escrevendo o arquivo do deploy descriptor */
      writeEjbJar(jTextFieldDir.getText(), jTextFieldPacote.getText(),
                  ejbJar.toString());
      /*
       * Gerando o Deployer Descriptor weblogib-ejb-jar.xml
       */
      StringBuffer weblogicEjbJar = new StringBuffer("");
      /* Iniciando o Deploy Descriptor */
      beginWebLogigEjbJar(tables, jTextFieldNomeEJB.getText(), weblogicEjbJar);
      /* Escrevendo o deploy descriptor */
      writeWeblogicEjbJar(jTextFieldDir.getText(), jTextFieldPacote.getText(),
                          weblogicEjbJar.toString());
      /*
       * Gerando o Deployer Descriptor: weblogic-cmp-rdbms-jar.xml
       */
      StringBuffer weblogicCmpRdbmsJar = new StringBuffer("");
      beginWeblogicCmpRdbmsJar(dbmd, tables, tablesFields,
                               jTextFieldDataSource.getText(),
                               weblogicCmpRdbmsJar);
      writeWeblogicCmlRdbmsJar(jTextFieldDir.getText(),
                               jTextFieldPacote.getText(),
                               weblogicCmpRdbmsJar.toString());
      /* Gerando o arquivo build.xml usado pela API ant para realizar o deploy */
      StringBuffer buildXml = new StringBuffer();
      beginBuildXml(tables, jTextFieldPacote.getText(),
                    jTextFieldNomeEJB.getText(), buildXml);
      writeBuildXml(jTextFieldDir.getText(), buildXml.toString());
      addLog("Entity Beans gerados.");
  }

  void jButtonEntityBeans_actionPerformed(ActionEvent e) {
		try {
      beanGenerate(connection);
		} catch ( Exception ex ) {
			addLog( "Erro na geração do bean: " + ex.getMessage());
		}
  }

  void jButtonSair_actionPerformed(ActionEvent e) {
    close();
    System.exit(1);
  }


  /**
   * Classe representando um atributo para os EntityBeans
   */
  public class Field implements Comparable {
    public int order;
    public String name;
    public short type;
    public String typeName;
    public int nullable;

    public Field (int order, String name) {
      this.order = order;
      this.name = name;
    }

    public Field (int order, String name, short type, String typeName,
                  int nullable) {
      this(order,name);
      this.type = type;
      this.typeName = typeName;
      this.nullable = nullable;
    }

    public boolean equals (Object obj) {
      boolean resp = false;
      if (obj instanceof Field) {
        Field field = (Field) obj;
        resp = this.name.equalsIgnoreCase(field.name);
      }
      return resp;
    }

    public int compareTo (Object obj) {
      int resp = 0;
      Field field = (Field) obj;
      if (this.order < field.order) {
        resp = -1;
      } else if (this.order > field.order) {
        resp = 1;
      }
      return resp;
    }

    public String toString () {
      return "[" + order + ", " + name + ", " + type + ", " + typeName + ", " +
              nullable + "]";
    }
  }
}