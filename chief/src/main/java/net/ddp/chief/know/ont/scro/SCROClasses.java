package net.ddp.chief.know.ont.scro;

import net.ddp.chief.know.ont.OntologyClass;

public enum SCROClasses implements OntologyClass {
	ABSTRACT_CLASS("AbstractClass"),
	ABSTRACT_LOCAL_CLASS("AbstractLocalClass"),
	ABSTRACT_MEMBER_CLASS("AbstractMemberClass"),
	ABSTRACT_METHOD("AbstractMethod"),
	ACCESS_CONTROL("AccessControl"),
	ACCESS_MODIFIER("AccessModifier"),
	ANNOTATION_TYPE("AnnotationType"),
	ANONYMOUS_CLASS("AnonymousClass"),
	ASSIGNMENT_STATEMENT("AssignmentStatement"),
	CLASS_TYPE("ClassType"),
	COMPILATION_UNIT("CompilationUnit"),
	COMPLEX_DATA_TYPE("ComplexDataType"),
	CONCRETE_CLASS("ConcreteClass"),
	CONSTRUCTOR("Constructor"),
	CONTROL_STRUCTURE("ControlStructure"),
	DATATYPE("DataType"),
	DECLARATION_STATEMENT("DeclarationStatement"),
	DO_WHILE_STATEMENT("Do-While-Statement"),
	ENUM_TYPE("EnumType"),
	FIELD("Field"),
	FINAL_CLASS("FinalClass"),
	FINAL_LOCAL_CLASS("FinalLocalClass"),
	FINAL_MEMBER_CLASS("FinalMemberClass"),
	FINAL_METHOD("FinalMethod"),
	FOR_STATEMENT("ForStatement"),
	IF_STATEMENT("IfStatement"),
	INNER_CLASS("InnerClass"),
	INSTANCE_FIELD("InstanceField"),
	INSTANCE_METHOD("InstanceMethod"),
	INTERFACE_TYPE("InterfaceType"),
	LOCAL_CLASS("LocalClass"),
	LOCAL_VARIABLE("LocalVariable"),
	METHOD("Method"),
	NON_STATIC_MEMBER_CLASS("NonStaticMemberClass"),
	PACKAGE("Package"),
	PACKAGE_PRIVATE("Package-Private"),
	PARAMETER("Parameter"),
	PRIMITIVE_DATA_TYPE("PrimitiveDataType"),
	PRIVATE_MODIFIER("PrivateModifier"),
	PROGRAM("Program"),
	PROTECTED_MODIFIER("ProtectedModifier"),
	PUBLIC_MODIFIER("PublicModifier"),
	REPETITION_STRUCTURE("RepetitionStructure"),
	SELECTION_STRUCTURE("SelectionStructure"),
	SEQUENCE_STRUCTURE("SequenceStructure"),
	STATIC_FIELD("StaticField"),
	STATIC_MEMBER_ANNOTATION("StaticMemberAnnotation"),
	STATIC_MEMBER_CLASS("StaticMemberClass"),
	STATIC_MEMBER_ENUM("StaticMemberEnum"),
	STATIC_MEMBER_INTERFACE("StaticMemberInterface"),
	STATIC_METHOD("StaticMethod"),
	STRUCTURED_DATATYPE("StructuredDataType"),
	SWITCH_STATEMENT("SwitchStatement"),
	UNSTRUCTURED_DATATYPE("UnstructuredDataType"),
	WHILE_STATEMENT("WhileStatement");
	
	/**
	 * The Namespace of the property.
	 */
	private static final String NAMESPACE = "http://www.cs.uwm.edu/~alnusair/ontologies/scro.owl#";

	/**
	 * The URI of the Object Property.
	 */
	private final String theURI;
	
	private SCROClasses(String aURI)
	{
		theURI = aURI;
	}
	
	public String getURI()
	{
		return NAMESPACE + theURI;
	}
	
}
