package org.sitenv.vocabularies.loader.loinc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.sitenv.vocabularies.constants.VocabularyConstants;
import org.sitenv.vocabularies.data.Vocabulary;
import org.sitenv.vocabularies.data.VocabularyDataStore;
import org.sitenv.vocabularies.loader.Loader;
import org.sitenv.vocabularies.loader.LoaderManager;
import org.sitenv.vocabularies.model.impl.Icd9CmDxModel;
import org.sitenv.vocabularies.model.impl.Icd9CmSgModel;
import org.sitenv.vocabularies.model.impl.LoincModel;
import org.sitenv.vocabularies.model.impl.SnomedModel;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

public class LoincLoader implements Loader {

	private static Logger logger = Logger.getLogger(LoincLoader.class);
	

	static {
		LoaderManager.getInstance()
				.registerLoader(VocabularyConstants.LOINC_CODE_NAME, LoincLoader.class);
		System.out.println("Loaded: " + VocabularyConstants.LOINC_CODE_NAME + "(" + VocabularyConstants.LOINC_CODE_SYSTEM + ")");
	}

	public Vocabulary load(File file, OObjectDatabaseTx dbConnection) {
		
		Vocabulary loinc = new Vocabulary(LoincModel.class, this.getCodeSystem());

		logger.debug("Loading LOINC File: " + file.getName());

		BufferedReader br = null;
		
		try {
			logger.info("Truncating LoincModel Datastore...");
			
			VocabularyDataStore.truncateModel(dbConnection, LoincModel.class);
			
			logger.info("LoincModel Datastore Truncated... records remaining: " + VocabularyDataStore.getRecordCount(dbConnection, LoincModel.class));
			

			
			int count = 0;

			br = new BufferedReader(new FileReader(file));
			String available;
			while ((available = br.readLine()) != null) {
				if (count++ == 0) {
					continue; // skip header row
				} else {

					String[] line = available.split(",");
					String code = line[0].replace("\"", "").toUpperCase();
					String name = line[1].replace("\"", "").toUpperCase();
					
					LoincModel model = dbConnection.newInstance(LoincModel.class);;
					model.setCode(code);
					model.setDisplayName(name);
					
					dbConnection.save(model);
				}


			}
			
			VocabularyDataStore.updateIndexProperties(dbConnection, LoincModel.class);
			
			
			logger.info("LoincModel Loading complete... records existing: " + VocabularyDataStore.getRecordCount(dbConnection, LoincModel.class));
			
			
		} catch (FileNotFoundException e) {
			// TODO: log4j
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			Runtime r = Runtime.getRuntime();
			r.gc();
		}

		return loinc;
		
	}
	
	
	public String getCodeName() {
		return VocabularyConstants.LOINC_CODE_NAME;
	}

	public String getCodeSystem() {
		return VocabularyConstants.LOINC_CODE_SYSTEM;
	}
}
