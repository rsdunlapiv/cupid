package org.earthsystemmodeling.cupid.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.earthsystemmodeling.cupid.nuopc.ESMFQuery;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.photran.core.IFortranAST;
import org.eclipse.photran.internal.core.parser.ASTModuleNode;
import org.eclipse.photran.internal.core.parser.ASTSubroutineSubprogramNode;
import org.eclipse.photran.internal.core.vpg.PhotranVPG;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public class ESMFQueryTest {

	@Rule
	public Timeout globalTimeout = new Timeout(30, TimeUnit.SECONDS);

	private static IProject PROJECT_NUOPC_PROTOTYPES;
	
	@BeforeClass
	public static void setUp() throws CoreException, IOException, InterruptedException {
		PROJECT_NUOPC_PROTOTYPES = TestHelpers.createFortranProjectFromFolder("target/ESMF_7_0_0", "ESMF_7_0_0");
	}
	
	@AfterClass
	public static void tearDown() throws CoreException {
		PROJECT_NUOPC_PROTOTYPES.delete(true, true, null);
	}
	
	@Test
	public void ASTTest() throws CoreException {
		IProject p = TestHelpers.createEmptyFortranProject("ASTTest");
		IFile f = p.getFile("blank.F90");
		f.create(new ByteArrayInputStream(new byte[0]), true, new NullProgressMonitor());
		
		PhotranVPG.getInstance().ensureVPGIsUpToDate(new NullProgressMonitor());
		IFortranAST ast = PhotranVPG.getInstance().acquireTransientAST(f);
		assertNotNull(ast);
		assertNotNull(ast.getRoot());
	}
	
	
	@Test
	public void findESMFMethods() {
		IFile f;
		IFortranAST ast;
		List<ASTSubroutineSubprogramNode> results;
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("atm.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		results = Lists.newArrayList(
					ESMFQuery.findESMFMethods((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0)));
		assertEquals("Two subroutines with ESMF signatures", 2, results.size());
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("esm.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		results = Lists.newArrayList(
					ESMFQuery.findESMFMethods((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0)));
		assertEquals("Two subroutines with ESMF signatures", 2, results.size());
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnMedPetListProto").getFile("ocn.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		results = Lists.newArrayList(
					ESMFQuery.findESMFMethods((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0)));
		assertEquals("3 subroutines with ESMF signatures", 3, results.size());
		
	}
	
	@Test
	public void findESMFEntryPoint() {
		IFile f;
		IFortranAST ast;
		List<ASTSubroutineSubprogramNode> results;
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("atm.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		results = Lists.newArrayList(
					ESMFQuery.findESMFEntryPoints((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0)));
		assertEquals("Two ESMF entry points", 2, results.size());
		List<String> names = Lists.newArrayList();
		names.add(results.get(0).getSubroutineStmt().getSubroutineName().getSubroutineName().getText());
		names.add(results.get(1).getSubroutineStmt().getSubroutineName().getSubroutineName().getText());
		assertThat("Entry point name matches", names.contains("InitializeP1"));
		assertThat("Entry point name matches", names.contains("InitializeP2"));

		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("esm.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		results = Lists.newArrayList(
					ESMFQuery.findESMFEntryPoints((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0)));
		assertEquals("No ESMF entry points", 0, results.size());
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnMedPetListProto").getFile("ocn.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		results = Lists.newArrayList(
					ESMFQuery.findESMFEntryPoints((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0)));
		assertEquals("Two ESMF entry points", 2, results.size());
		
	}
	
	@Test
	public void findESMFSetServices() {
		IFile f;
		IFortranAST ast;
		ASTSubroutineSubprogramNode result;
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("atm.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		result = ESMFQuery.findESMFSetServices((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0));
		assertEquals("SetServices method name", "SetServices", result.getName());
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("ocn.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		result = ESMFQuery.findESMFSetServices((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0));
		assertEquals("SetServices method name", "SetServices", result.getName());
		
		f = PROJECT_NUOPC_PROTOTYPES.getFolder("AtmOcnProto").getFile("esm.F90");
		ast = PhotranVPG.getInstance().acquireTransientAST(f);
		result = ESMFQuery.findESMFSetServices((ASTModuleNode) ast.getRoot().getProgramUnitList().get(0));
		assertEquals("SetServices method name", "SetServices", result.getName());
				
		
	}
	
	
}
