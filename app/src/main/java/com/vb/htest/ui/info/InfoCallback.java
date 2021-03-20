package com.vb.htest.ui.info;

import com.vb.htest.data.network.model.InfoResponse;

public interface InfoCallback {

    void success(InfoResponse infoResponse);

    void error(InfoResponse infoResponse);
}
