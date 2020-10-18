package br.edu.dmos5.projeto_isadora_schutzer_dmos5.api;

import br.edu.dmos5.projeto_isadora_schutzer_dmos5.model.Horoscopo;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AstroAPI {
    String endPoint = "https://aztro.sameerkumar.website";

    @POST("/")
    Call<Horoscopo> horoscopo(@Query("sign") String sign, @Query("day") String day);
}
