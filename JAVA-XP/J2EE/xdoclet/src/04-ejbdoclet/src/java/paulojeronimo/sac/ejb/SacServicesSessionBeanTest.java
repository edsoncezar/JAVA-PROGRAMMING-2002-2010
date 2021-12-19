package paulojeronimo.sac.ejb;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import paulojeronimo.sac.interfaces.*;

public class SacServicesSessionBeanTest extends TestCase {
    private SacServicesSessionHome sacServicesSessionHome;

    public static Test suite() {
        TestSuite testSuite = new TestSuite(SacServicesSessionBeanTest.class.getName());
        testSuite.addTestSuite(SacServicesSessionBeanTest.class);
        return testSuite;
    }   

    public SacServicesSessionBeanTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        sacServicesSessionHome = (SacServicesSessionHome) SacServicesSessionUtil.getHome();
    }

    public void testGetCentrais() throws Exception {
        SacServicesSession sacServicesSession = sacServicesSessionHome.create();
        Collection c = sacServicesSession.getCentrais();
        assertNotNull(c);

        System.out.println("\n\ngetCentrais()");
        Iterator i = c.iterator();
        while (i.hasNext()) {
            CentralData centralData = (CentralData) i.next();
            System.out.println(centralData);
        }
    }

    public void testGetCentrais2() throws Exception {
        final String usuario = "usu1";

        SacServicesSession sacServicesSession = sacServicesSessionHome.create();
        Collection c = sacServicesSession.getCentrais(usuario);
        assertNotNull(c);

        System.out.println("\n\ngetCentrais(" + usuario + ")");
        Iterator i = c.iterator();
        while (i.hasNext()) {
            CentralData centralData = (CentralData) i.next();
            System.out.println(centralData);
        }
    }

    public void testGetClassesServicos() throws Exception {
        SacServicesSession sacServicesSession = sacServicesSessionHome.create();
        Collection c = sacServicesSession.getClassesServicos();
        assertNotNull(c);

        System.out.println("\n\ngetClassesServicos()");
        Iterator i = c.iterator();
        while (i.hasNext()) {
            ClasseServicoData classeServicoData = (ClasseServicoData) i.next();
            System.out.println(classeServicoData);
        }
    }

    public void testGetServicos() throws Exception {
        final int classeServico = 1;

        SacServicesSession sacServicesSession = sacServicesSessionHome.create();
        Collection c = sacServicesSession.getServicos(classeServico);
        assertNotNull(c);

        System.out.println("\n\ngetServicos(" + classeServico + ")");
        Iterator i = c.iterator();
        while (i.hasNext()) {
            ServicoData servicoData = (ServicoData) i.next();
            System.out.println(servicoData);
        }
    }

    public void testGetComandos() throws Exception {
        final int idServico = 1;
        final int idVersao = 1;

        SacServicesSession sacServicesSession = sacServicesSessionHome.create();
        Collection c = sacServicesSession.getComandos(idVersao, idServico);
        assertNotNull(c);

        System.out.println("\n\ngetComandos(" + idVersao + ", " + idServico + ")");
        Iterator i = c.iterator();
        while (i.hasNext()) {
            ComandoData comandoData = (ComandoData) i.next();
            System.out.println(comandoData);
        }
    }
}
