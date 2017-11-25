package com.ervic.mac.gobvalle.API;

import com.ervic.mac.gobvalle.Models.MenuResponse;
import com.ervic.mac.gobvalle.Models.RequestTypeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ervic on 11/24/17.
 */

public interface ApiService {

    @GET("Bloques/menus")
    Call<MenuResponse> getMenu();

    @GET("Pasaporte/requestTypes")
    Call<RequestTypeResponse> getRequestTypes();

}
