package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntologyProperty;

public enum ObjectProperties implements OntologyProperty {
	ANNOTATED_BY("annotatedBy"),
	ANNOTATES("annotates"),
	BELONGS_TO_PKG("belongsToPackage"),
	CONTAINS_C_UNIT("containsCUnit"),
	CONTAINS_LOOP("containsLoop"),
	CONTAINS_REF_TYPE("containsRefType"),
	CONTAINS_SELECTION("containsSelection"),
	CONTAINS_SEQUENCE("containsSequence"),
	DEFINED_IN_C_UNIT("definedInCUnit"),
	HAS_ABSTRACT_METHOD("hasAbstractMethod"),
	HAS_ACCESS_CONTROL("hasAccessControl"),
	HAS_ACTUAL_OUTPUT_TYPE("hasActualOutputType"),
	HAS_ANONYMOUS_CLASS("hasAnonymousClass"),
	HAS_COMPILATION_UNIT("hasCompilationUnit"),
	HAS_COMPLEX_DATATYPE("hasComplexDataType"),
	HAS_CONSTRUCTOR("hasConstructor"),
	HAS_DATATYPE("hasDataType"),
	HAS_FIELD("hasField"),
	HAS_FINAL_METHOD("hasFinalMethod"),
	HAS_INPUT_TYPE("hasInputType"),
	HAS_INSTANCE_FIELD("hasInstanceField"),
	HAS_INSTANCE_METHOD("hasInstanceMethod"),
	HAS_LOCAL_CLASS("hasLocalClass"),
	HAS_LOCAL_VARIABLE("hasLocalVariable"),
	HAS_MEMBER("hasMember"),
	HAS_METHOD("hasMethod"),
	HAS_OUTER_CLASS("hasOuterClass"),
	HAS_OUTPUT_TYPE("hasOutputType"),
	HAS_PKG_MEMBER("hasPackageMember"),
	HAS_PARAMETER("hasParameter"),
	HAS_PART("hasPart"),
	HAS_STATIC_FIELD("hasStaticField"),
	HAS_STATIC_METHOD("hasStaticMethod"),
	HAS_STRUCT_DATATYPE("hasStructuredDataType"),
	HAS_SUB_TYPE("hasSubType"),
	HAS_SUPER_TYPE("hasSuperType"),
	IMPLEMENTED_BY("implementedBy"),
	IMPLEMENTS("implements"),
	IMPORTED_BY("importedBy"),
	IMPORTS("imports"),
	INHERITED_BY("inheritedBy"),
	INHERITS("inherits"),
	INVOKES_METHOD("invokesMethod"),
	IS_ABSTRACT_METHOD_OF("isAbstractMethodOf"),
	IS_ACCESS_CONTROL_OF("isAccessControlOf"),
	IS_ANONYMOUS_CLASS_OF("isAnonymousClassOf"),
	IS_COMPLEX_DATATYPE_OF("isComplexDataTypeOf"),
	IS_CONSTRUCTOR_OF("isConstructorOf"),
	IS_DATATYPE_OF("isDataTypeOf"),
	IS_FIELD_OF("isFieldOf"),
	IS_FINAL_METHOD_OF("isFinalMethodOf"),
	IS_INPUT_TYPE_OF("isInputTypeOf"),
	IS_INSTANCE_FIELD_OF("isInstanceFieldOf"),
	IS_INSTANCE_METHOD_OF("isInstanceMethodOf"),
	IS_LOCAL_CLASS_OF("isLocalClassOf"),
	IS_LOCAL_VARIABLE_OF("isLocalVariableOf"),
	IS_MEMBER_OF("isMemberOf"),
	IS_METHOD_OF("isMethodOf"),
	IS_PACKAGE_MEMBER_OF("isPackageMemberOf"),
	IS_PARAMETER_OF("isParameterOf"),
	IS_PART_OF("isPartOf"),
	IS_STATIC_FIELD_OF("isStaticFieldOf"),
	IS_STATIC_METHOD_OF("isStaticMethodOf"),
	IS_USED_BY("isUsedBy"),
	METHOD_INVOKED_BY("methodInvokedBy"),
	METHOD_OVERRIDDEN_BY("methodOverriddenBy"),
	METHOD_OVERRIDES("methodOverrides"),
	USE("use");
	
	/**
	 * The Namespace of the property.
	 */
	private static final String NAMESPACE = "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#";
	
	/**
	 * The URI of the Object Property.
	 */
	private final String theURI;
	
	private ObjectProperties(String aURI)
	{
		theURI = aURI;
	}
	
	public String getURI()
	{
		return NAMESPACE + theURI;
	}
}
