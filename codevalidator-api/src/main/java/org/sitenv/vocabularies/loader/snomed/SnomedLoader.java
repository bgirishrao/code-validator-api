package org.sitenv.vocabularies.loader.snomed;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.sitenv.vocabularies.data.Vocabulary;
import org.sitenv.vocabularies.loader.Loader;
import org.sitenv.vocabularies.loader.LoaderManager;

public class SnomedLoader implements Loader {

	private static Logger logger = Logger.getLogger(SnomedLoader.class);
	
	static {
		LoaderManager.getInstance().registerLoader("SNOMED", SnomedLoader.class);
		System.out.println("Loaded SNOMED");
	}
	
	public Vocabulary load(File file) {
		Vocabulary snomed = new Vocabulary(file.getName());
		
		logger.debug("Loading SNOMED File: " + file.getName());
		
		try 
		{
			Scanner scan = new Scanner(file);
			int count = 0;
			
			while (scan.hasNextLine())
			{
				if (count++ == 0) 
				{
					continue; // skip header row
				}
				else
				{
					String[] line = scan.nextLine().split("\t");
					snomed.getCodes().add(line[4].toUpperCase());
					snomed.getDisplayNames().add(line[2].toUpperCase());
				}
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO: log4j
			e.printStackTrace();
		}
		
		
		return snomed;
	}

}
