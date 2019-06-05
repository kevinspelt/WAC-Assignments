package nl.hu.v1wac.template.persistence;

import nl.hu.v1wac.template.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {
    private PreparedStatement setStatement(PreparedStatement stmt, Country country) {
        try {
            stmt.setString(1, country.getCode());
            stmt.setString(2, country.getName());
            stmt.setString(3, country.getContinent());
            stmt.setString(4, country.getRegion());
            stmt.setDouble(5, country.getSurface());
            stmt.setInt(6, country.getPopulation());
            stmt.setString(7, country.getGovernment());
            stmt.setString(8, country.getCapital());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    private Country getCountryWithResultSet(ResultSet rs) {
        Country country = null;
        try {
            while (rs.next()) {
                country = new Country(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("continent"),
                        rs.getString("region"),
                        rs.getDouble("surfacearea"),
                        rs.getInt("population"),
                        rs.getString("governmentform"),
                        rs.getString("capital")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    private List<Country> getCountriesWithResultSet(ResultSet rs) {
        List<Country> countries = new ArrayList<>();
        try {
            while (rs.next()) {
                Country country = new Country(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("continent"),
                        rs.getString("region"),
                        rs.getDouble("surfacearea"),
                        rs.getInt("population"),
                        rs.getString("governmentform"),
                        rs.getString("capital")
                );
                countries.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    @Override
    public boolean save(Country country) {
        boolean returnBool = false;
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "INSERT INTO country (code, name, continent, region, surfacearea, population, governmentform, capital) " +
                    "VALUES (?,?,?,?,?,?,?,?)");
            returnBool = setStatement(stmt, country).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnBool;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "SELECT code, name, continent, region, surfacearea, population, governmentform, capital " +
                    "FROM country");
            ResultSet rs = stmt.executeQuery();
            countries = getCountriesWithResultSet(rs);
            while (rs.next()) {
                System.out.println(rs.getString("capital"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    @Override
    public Country findByCode(String code) {
        Country country = new Country();
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "SELECT code, name, continent, region, surfacearea, population, governmentform, capital " +
                    "FROM country " +
                    "WHERE code='" + code + "'");
            ResultSet rs = stmt.executeQuery();
            country = getCountryWithResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    @Override
    public List<Country> find10LargestPopulations() {
        List<Country> countries = new ArrayList<>();
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "SELECT code, name, continent, region, surfacearea, population, governmentform, capital " +
                    "FROM country " +
                    "ORDER BY population DESC " +
                    "FETCH FIRST 10 ROWS ONLY");
            ResultSet rs = stmt.executeQuery();
            countries = getCountriesWithResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    @Override
    public List<Country> find10LargestSurfaces() {
        List<Country> countries = new ArrayList<>();
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "SELECT code, name, continent, region, surfacearea, population, governmentform, capital " +
                    "FROM country " +
                    "ORDER BY surface DESC " +
                    "FETCH FIRST 10 ROWS ONLY");
            ResultSet rs = stmt.executeQuery();
            countries = getCountriesWithResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }

    @Override
    public boolean update(Country country) {
        boolean returnBool = false;
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "UPDATE country " +
                    "SET code=?, name=?, continent=?, region=?, surfacearea=?, population=?, governmentform=?, capital=? " +
                    "WHERE code='" + country.getCode() + "'");
            returnBool = setStatement(stmt, country).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnBool;
    }

    @Override
    public boolean delete(Country country) {
        boolean returnBool = false;
        try (Connection conn = super.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("" +
                    "DELETE FROM country " +
                    "WHERE code='" + country.getCode() + "'");
            returnBool = stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnBool;
    }
}
