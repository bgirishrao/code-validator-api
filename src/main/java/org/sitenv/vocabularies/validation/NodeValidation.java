package org.sitenv.vocabularies.validation;

import java.util.List;

import javax.xml.xpath.XPath;

import org.sitenv.vocabularies.configuration.ConfiguredValidator;
import org.sitenv.vocabularies.validation.dto.VocabularyValidationResult;
import org.w3c.dom.Node;

public interface NodeValidation {
	List<VocabularyValidationResult> validateNode(ConfiguredValidator configuredValidator, XPath xpath, Node node, int nodeIndex);
}
