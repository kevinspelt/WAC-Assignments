package nl.hu.v1wac.template.webservices;

import nl.hu.v1wac.template.model.Country;
import nl.hu.v1wac.template.model.ServiceProvider;
import nl.hu.v1wac.template.model.WorldService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/countries")
public class WorldResource {

    //Creates the Object of country
    public JsonObjectBuilder getCountryObjectBuilder(Country country) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder
                .add("code", country.getCode())
                .add("name", country.getName())
                .add("capital", country.getCapital())
                .add("continent", country.getContinent())
                .add("iso3", country.getIso3())
                .add("population", country.getPopulation())
                .add("region", country.getRegion())
                .add("surface", country.getSurface())
                .add("government", country.getGovernment())
                .add("lat", country.getLatitude())
                .add("lng", country.getLongitude());
        return jsonObjectBuilder;
    }

    @GET
    @Produces("application/json")
    public String getCountries() {
        WorldService worldService = ServiceProvider.getWorldService();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Country country : worldService.getAllCountries()) {
            jsonArrayBuilder.add(getCountryObjectBuilder(country));
        }
        return jsonArrayBuilder.build().toString();
    }

    @GET
    @Path("{code}")
    @Produces("application/json")
    public String getCountryInfo(@PathParam("code") String code) {
        WorldService worldService = ServiceProvider.getWorldService();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder = getCountryObjectBuilder(worldService.getCountryByCode(code));
        return jsonObjectBuilder.build().toString();
    }

    @GET
    @Path("/largestsurfaces")
    @Produces("application/json")
    public String getLargestSurfaces() {
        WorldService worldService = ServiceProvider.getWorldService();
        List<Country> largestSurfaces = worldService.get10LargestSurfaces();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Country country : largestSurfaces) {
            jsonArrayBuilder.add(getCountryObjectBuilder(country));
        }
        return jsonArrayBuilder.build().toString();
    }

    @GET
    @Path("/largestpopulation")
    @Produces("application/json")
    public String getLargestPopulation() {
        WorldService worldService = ServiceProvider.getWorldService();
        List<Country> largestPopulation = worldService.get10LargestPopulations();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Country country : largestPopulation) {
            jsonArrayBuilder.add(getCountryObjectBuilder(country));
        }
        return jsonArrayBuilder.build().toString();
    }
}
