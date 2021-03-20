package com.vb.htest.data.network.service;

import androidx.annotation.Nullable;

public interface AlarStudioService {
    String makeRequest(@Nullable String additionalPath, int timeout);
    String makeRequest(@Nullable String additionalPath, @Nullable String parameters, int timeout);
}
