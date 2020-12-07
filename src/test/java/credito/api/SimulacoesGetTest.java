package credito.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

//import org.junit.FixMethodOrder;
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@TestMethodOrder(Sorters)
class SimulacoesGetTest {

    private static String Simulacao;

    @BeforeAll
    static void preCondicao(){
        baseURI = "http://localhost";
        basePath = "/api/v1";
        port = 8088;
    }

    @Test
    void validarListaDeSimulacoes() {
        when().
              get("/simulacoes").
        then().
              statusCode(200).
              log().all().
              body("[0].cpf", equalTo("66414919004"))
        ;
    }

    @Test
    void criarSimulacao(){
        //Simulacao =
        given().
                header("Content-Type", "application/json").
                log().all().
                //para conseguir fazer isso basta abrir e fechar aspas e copiar contrato dentro que vai automaticamente
                body("{\n" +
                        "  \"cpf\": \"05881469780\",\n" +
                        "  \"email\": \"email@email.com\",\n" +
                        "  \"nome\": \"Ricardo Veiga\",\n" +
                        "  \"parcelas\": 4,\n" +
                        "  \"seguro\": true,\n" +
                        "  \"valor\": 2000\n" +
                        "}").
        when().
                post("/simulacoes").
        then().
                statusCode(201).
                header("Location", "http://localhost:8088/api/v1/simulacoes/05881469780")
                //extract().path("cpf")
        ;
        //System.out.print(Simulacao);
    }

    @Test
    void buscarSimulacao(){
        Simulacao =
                when().
                        get("/simulacoes/05881469780").
                then().
                        statusCode(200).
                        header("Content-Type", "application/json").
//Funcional
                        body("nome", equalTo("Ricardo Veiga")).
                        body("cpf", equalTo("05881469780")).
                        body("email", equalTo("email@email.com")).
                        body("valor", is(2000.0f)).
                        body("parcelas", equalTo(4)).
                        body("seguro", equalTo(true)).
                        extract().path("cpf")
        ;
        System.out.print(Simulacao);
    }

    @Test
    void deletarSimulacao(){
                when().
                        delete("/simulacoes" + Simulacao).
                then().
                        statusCode(204)
                ;
    }

}
