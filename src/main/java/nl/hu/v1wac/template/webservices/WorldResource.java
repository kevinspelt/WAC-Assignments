package nl.hu.v1wac.template.webservices;

import nl.hu.v1wac.template.model.Country;
import nl.hu.v1wac.template.model.ServiceProvider;
import nl.hu.v1wac.template.model.WorldService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/countries")
public class WorldResource {
    WorldService worldService = ServiceProvider.getWorldService();

    @GET
    @Produces("application/json")
    public Response getCountries() {
        List<Country> countries = worldService.getAllCountries();
        return Response.ok(countries).build();
    }

    @GET
    @Path("{code}")
    @Produces("application/json")
    public Response getCountryInfo(@PathParam("code") String code) {
        Country country = worldService.getCountryByCode(code);
        return Response.ok(country).build();
    }

    @GET
    @Path("/largestsurfaces")
    @Produces("application/json")
    public Response getLargestSurfaces() {
        List<Country> countries = worldService.get10LargestSurfaces();
        return Response.ok(countries).build();
    }

    @GET
    @Path("/largestpopulation")
    @Produces("application/json")
    public Response getLargestPopulation() {
        List<Country> countries = worldService.get10LargestPopulations();
        return Response.ok(countries).build();
    }

    @POST
    @Produces("application/json")
    public Response addCountry(
            @FormParam("code") String code,
            @FormParam("name") String name,
            @FormParam("capital") String capital,
            @FormParam("region") String region,
            @FormParam("surface") int surface,
            @FormParam("population") int population) {
        Country country = new Country(code, name, "x", region, surface, population, "x", capital);
        worldService.saveCountry(country);
        return Response.ok(country).build();
    }

    @PUT
    @Path("{code}")
    @Produces("application/json")
    public Response editCountryInfo(
            @PathParam("code") String code,
            @FormParam("name") String name,
            @FormParam("capital") String capital,
            @FormParam("region") String region,
            @FormParam("surface") int surface,
            @FormParam("population") int population) {
        Country country = new Country(code, name, "x", region, surface, population, "x", capital);
        worldService.updateCountry(country);
        return Response.ok(country).build();
    }

    @DELETE
    @Path("{code}")
    @Produces("application/json")
    public Response deleteCountry(
            @PathParam("code") String code) {
        Country country = worldService.getCountryByCode(code);
        worldService.deleteCountry(country);
        return Response.ok(country).build();
    }
}
