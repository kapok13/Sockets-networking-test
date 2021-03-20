package com.vb.htest.data.network.service;

import org.junit.Test;

public class AlarStudioServiceImplTest {

    @Test
    public void connect() {
        AlarStudioService alarStudioService = AlarStudioServiceImpl.getInstance();
        System.out.println("AlarStudioServiceTest" + alarStudioService.makeRequest("auth.cgi", "username=test&password=123", 40000));
    }
}