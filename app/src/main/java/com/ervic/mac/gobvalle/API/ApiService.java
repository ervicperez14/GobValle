package com.ervic.mac.gobvalle.API;

import com.ervic.mac.gobvalle.Models.MenuResponse;
import com.ervic.mac.gobvalle.Models.RequestTypeResponse;
import com.ervic.mac.gobvalle.Models.ResponseCancel;
import com.ervic.mac.gobvalle.Models.ResponseProcess;
import com.ervic.mac.gobvalle.Models.ResponseToken;
import com.ervic.mac.gobvalle.Models.ResponseVerificaPago;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @FormUrlEncoded
    @POST("Pasaporte/checkPayment")
    Call<ResponseVerificaPago> getCheckPayment(@Header("nxtoken") String token, @Field("identificacion")String id,@Field("fecha_de_pago")String fecha);

    @FormUrlEncoded
    @POST("usuarios/login?movil=1")
    Call<ResponseToken> getTokenSystem(@Field("login")String login, @Field("password")String password);

    @FormUrlEncoded
    @POST("Pasaporte/consult")
    Call<ResponseProcess> setOptionPasaporte(@Field("fecha_pago") String fecha_pago, @Field("identificacion") String identificacion, @Field("process") String process, @Header("nxtoken") String nxtoken);

    @FormUrlEncoded
    @POST("Pasaporte/cancelAppointment")
    Call<ResponseCancel> cancel(@Field("id") String id_cita, @Field("description") String description, @Header("nxtoken") String nxtoken);


}
