/*
Creates country objects and inserts them in the allCountries list
 */

package nl.hu.v1wac.template.model;

import nl.hu.v1wac.template.persistence.CountryPostgresDaoImpl;

import java.util.List;

public class WorldService {
	CountryPostgresDaoImpl countryPostgresDao = new CountryPostgresDaoImpl();

	public List<Country> getAllCountries() {
		return countryPostgresDao.findAll();
	}
	
	public List<Country> get10LargestPopulations() {
		return countryPostgresDao.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces() {
		return countryPostgresDao.find10LargestSurfaces();
	}
	
	public Country getCountryByCode(String code) {
		return countryPostgresDao.findByCode(code);
	}

	public boolean saveCountry (Country country) { return countryPostgresDao.save(country); }

	public boolean updateCountry (Country country) { return  countryPostgresDao.update(country); }

	public boolean deleteCountry (Country country) { return countryPostgresDao.delete(country); }
}
