package com.vb.htest.data.network.repository;

import com.vb.htest.data.network.model.AuthResponse;
import com.vb.htest.data.network.model.InfoResponse;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlarStudioRepoImplTest {

    @Test
    public void makeRepoAuthRequest() {
        AuthResponse response = AlarStudioRepoImpl.getInstance().makeAuthRequest("test", "123");
        assertEquals(response.getStatus(), "ok");
    }

    @Test
    public void makeInfoRequest() {
        InfoResponse infoResponse = AlarStudioRepoImpl.getInstance().makeInfoRequest("9327541908", "1");
        assertNotNull(infoResponse);
    }

}