package com.example.service_novigrad_livrable2;

import org.junit.Test;

import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    ServiceModal service = new ServiceModal("Hotelier",  "@1234", "Nom, Prenom, date", "ID, permis");
    ServiceModal service1 = new ServiceModal("12345",  "@1234", "No@@@##%#igrbglhjebg", "___hbkehbrf");
    ServiceModal service12 = new ServiceModal("Retrincur",  "@1234", "@@Arestation", "permisdeCond1@##");
    ServiceModal service34 = new ServiceModal("      ",  "@    ____", "L'homme;''", "ID, permis");


    @Test
    public void test_Service(){  assertEquals("Getting the service Name: ", "@1234", service.getServiceName() );  }

    @Test
    public void test_ServiceFormulaire34(){ assertEquals("Getting the service Model: ", "Hotelier", service.getServiceId());  }

    @Test
    public void test_ServiceDocuments(){ assertEquals("Getting the service Documents: ", "permisdeCond1@##", service12.getDocuments());  }

    @Test
    public void test_ServiceId(){ assertEquals("Getting the service Id: ", "      ", service34.getServiceId());  }

    @Test
    public void test_ServiceName(){ assertEquals("Getting the service Name: ", "@    ____", service34.getServiceName());  }



}