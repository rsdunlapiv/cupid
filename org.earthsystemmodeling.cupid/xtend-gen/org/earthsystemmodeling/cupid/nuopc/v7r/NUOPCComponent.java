package org.earthsystemmodeling.cupid.nuopc.v7r;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.earthsystemmodeling.cupid.annotation.Child;
import org.earthsystemmodeling.cupid.annotation.Label;
import org.earthsystemmodeling.cupid.annotation.MappingType;
import org.earthsystemmodeling.cupid.annotation.Name;
import org.earthsystemmodeling.cupid.annotation.Prop;
import org.earthsystemmodeling.cupid.nuopc.ASTQuery;
import org.earthsystemmodeling.cupid.nuopc.BasicCodeConcept;
import org.earthsystemmodeling.cupid.nuopc.CodeConcept;
import org.earthsystemmodeling.cupid.nuopc.CodeGenerationException;
import org.earthsystemmodeling.cupid.nuopc.ReverseEngineerException;
import org.earthsystemmodeling.cupid.util.CodeExtraction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.photran.core.IFortranAST;
import org.eclipse.photran.internal.core.FortranAST;
import org.eclipse.photran.internal.core.lexer.TokenList;
import org.eclipse.photran.internal.core.parser.ASTEmptyProgramNode;
import org.eclipse.photran.internal.core.parser.ASTExecutableProgramNode;
import org.eclipse.photran.internal.core.parser.ASTListNode;
import org.eclipse.photran.internal.core.parser.ASTModuleNode;
import org.eclipse.photran.internal.core.parser.ASTOnlyNode;
import org.eclipse.photran.internal.core.parser.ASTRenameNode;
import org.eclipse.photran.internal.core.parser.ASTUseStmtNode;
import org.eclipse.photran.internal.core.parser.IASTListNode;
import org.eclipse.photran.internal.core.parser.IModuleBodyConstruct;
import org.eclipse.photran.internal.core.parser.IProgramUnit;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@Label(label = "NUOPC Driver")
@MappingType("module")
@SuppressWarnings("all")
public abstract class NUOPCComponent extends CodeConcept<CodeConcept<?, ?>, ASTModuleNode> {
  @Label(label = "Generic Import")
  @MappingType("uses")
  public static class GenericImport extends CodeConcept<NUOPCComponent, ASTUseStmtNode> {
    @Name
    public String genericComp;
    
    @Prop
    @Label(label = "routineSetServices")
    public String routineSetServices;
    
    public Map<String, String> imports;
    
    public GenericImport(final NUOPCComponent parent, final String genericComp) {
      super(parent);
      this.genericComp = genericComp;
      String _prefix = this._parent.prefix();
      String _plus = (_prefix + "_SetServices");
      this.routineSetServices = _plus;
      this.imports = CollectionLiterals.<String, String>newHashMap();
    }
    
    @Override
    public CodeConcept<?, ?> forward() {
      NUOPCComponent.GenericImport _xblockexpression = null;
      {
        final ASTUseStmtNode usn = CodeConcept.ensureImport(this._parent.getASTRef(), this.genericComp, "SetServices", this.routineSetServices, false);
        this.setASTRef(usn);
        final BiConsumer<String, String> _function = (String k, String v) -> {
          this.setASTRef(CodeConcept.ensureImport(this.getASTRef(), k, v));
        };
        this.imports.forEach(_function);
        _xblockexpression = this;
      }
      return _xblockexpression;
    }
    
    @Override
    public NUOPCComponent.GenericImport reverse() {
      NUOPCComponent.GenericImport _xblockexpression = null;
      {
        final ASTModuleNode moduleNode = this._parent.getASTRef();
        Iterable<ASTUseStmtNode> _filter = Iterables.<ASTUseStmtNode>filter(moduleNode.getModuleBody(), ASTUseStmtNode.class);
        ASTUseStmtNode _findFirst = null;
        if (_filter!=null) {
          final Function1<ASTUseStmtNode, Boolean> _function = (ASTUseStmtNode it) -> {
            return Boolean.valueOf(ASTQuery.eic(it.getName(), this.genericComp));
          };
          _findFirst=IterableExtensions.<ASTUseStmtNode>findFirst(_filter, _function);
        }
        final ASTUseStmtNode usn = _findFirst;
        this.setASTRef(usn);
        boolean _equals = Objects.equal(usn, null);
        if (_equals) {
          return null;
        }
        this.genericComp = usn.getName().getText();
        IASTListNode<ASTOnlyNode> _onlyList = usn.getOnlyList();
        ASTOnlyNode _findFirst_1 = null;
        if (_onlyList!=null) {
          final Function1<ASTOnlyNode, Boolean> _function_1 = (ASTOnlyNode it) -> {
            return Boolean.valueOf(it.getName().getText().equalsIgnoreCase("SetServices"));
          };
          _findFirst_1=IterableExtensions.<ASTOnlyNode>findFirst(_onlyList, _function_1);
        }
        ASTOnlyNode on = _findFirst_1;
        boolean _notEquals = (!Objects.equal(on, null));
        if (_notEquals) {
          String _xifexpression = null;
          boolean _isRenamed = on.isRenamed();
          if (_isRenamed) {
            _xifexpression = on.getNewName().getText();
          } else {
            _xifexpression = on.getName().getText();
          }
          this.routineSetServices = _xifexpression;
        } else {
          IASTListNode<ASTRenameNode> _renameList = usn.getRenameList();
          ASTRenameNode _findFirst_2 = null;
          if (_renameList!=null) {
            final Function1<ASTRenameNode, Boolean> _function_2 = (ASTRenameNode it) -> {
              return Boolean.valueOf(it.getName().getText().equalsIgnoreCase("SetServices"));
            };
            _findFirst_2=IterableExtensions.<ASTRenameNode>findFirst(_renameList, _function_2);
          }
          ASTRenameNode rn = _findFirst_2;
          boolean _notEquals_1 = (!Objects.equal(rn, null));
          if (_notEquals_1) {
            this.routineSetServices = rn.getNewName().getText();
          }
        }
        _xblockexpression = this;
      }
      return _xblockexpression;
    }
  }
  
  protected String genericImport;
  
  public String name;
  
  @Label(label = "ESMF Import", sort = 1)
  @Child(forward = false)
  @MappingType("uses")
  public BasicCodeConcept<ASTUseStmtNode> importESMF;
  
  @Label(label = "NUOPC Import", sort = 2)
  @Child(forward = false)
  @MappingType("uses")
  public BasicCodeConcept<ASTUseStmtNode> importNUOPC;
  
  @Label(label = "Generic Import", sort = 3)
  @Child(forward = true)
  @MappingType("uses")
  public NUOPCComponent.GenericImport importNUOPCGeneric;
  
  public NUOPCComponent(final CodeConcept<?, ?> parent, final String genericImport) {
    this(parent, null, genericImport);
  }
  
  public NUOPCComponent(final CodeConcept<?, ?> parent, final IResource context, final String genericImport) {
    super(parent, context);
    try {
      this.genericImport = genericImport;
      boolean _equals = Objects.equal(genericImport, null);
      if (_equals) {
        throw new CodeGenerationException("Name of generic import of component cannot be null");
      }
      NUOPCComponent.GenericImport _genericImport = new NUOPCComponent.GenericImport(this, genericImport);
      this.importNUOPCGeneric = _genericImport;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public abstract String prefix();
  
  @Override
  public String name() {
    return this.name;
  }
  
  @Override
  public CodeConcept<?, ?> reverse() throws ReverseEngineerException {
    NUOPCComponent _xblockexpression = null;
    {
      IFortranAST ast = this.getAST();
      ASTExecutableProgramNode _root = null;
      if (ast!=null) {
        _root=ast.getRoot();
      }
      IASTListNode<IProgramUnit> _programUnitList = null;
      if (_root!=null) {
        _programUnitList=_root.getProgramUnitList();
      }
      Iterable<ASTModuleNode> _filter = null;
      if (_programUnitList!=null) {
        _filter=Iterables.<ASTModuleNode>filter(_programUnitList, ASTModuleNode.class);
      }
      ASTModuleNode _findFirst = null;
      if (_filter!=null) {
        final Function1<ASTModuleNode, Boolean> _function = (ASTModuleNode it) -> {
          IASTListNode<IModuleBodyConstruct> _moduleBody = it.getModuleBody();
          Iterable<ASTUseStmtNode> _filter_1 = null;
          if (_moduleBody!=null) {
            _filter_1=Iterables.<ASTUseStmtNode>filter(_moduleBody, ASTUseStmtNode.class);
          }
          boolean _exists = false;
          if (_filter_1!=null) {
            final Function1<ASTUseStmtNode, Boolean> _function_1 = (ASTUseStmtNode it_1) -> {
              return Boolean.valueOf(ASTQuery.eic(it_1.getName().getText(), this.genericImport));
            };
            _exists=IterableExtensions.<ASTUseStmtNode>exists(_filter_1, _function_1);
          }
          return Boolean.valueOf(_exists);
        };
        _findFirst=IterableExtensions.<ASTModuleNode>findFirst(_filter, _function);
      }
      this._astRef = _findFirst;
      NUOPCComponent _xifexpression = null;
      boolean _notEquals = (!Objects.equal(this._astRef, null));
      if (_notEquals) {
        NUOPCComponent _xblockexpression_1 = null;
        {
          this.name = this._astRef.getModuleStmt().getModuleName().getModuleName().getText();
          final Consumer<ASTUseStmtNode> _function_1 = (ASTUseStmtNode it) -> {
            boolean _eic = ASTQuery.eic(it.getName().getText(), "ESMF");
            if (_eic) {
              BasicCodeConcept<ASTUseStmtNode> _basicCodeConcept = new BasicCodeConcept<ASTUseStmtNode>(this, it);
              this.importESMF = _basicCodeConcept;
            } else {
              boolean _eic_1 = ASTQuery.eic(it.getName().getText(), "NUOPC");
              if (_eic_1) {
                BasicCodeConcept<ASTUseStmtNode> _basicCodeConcept_1 = new BasicCodeConcept<ASTUseStmtNode>(this, it);
                this.importNUOPC = _basicCodeConcept_1;
              } else {
                boolean _eic_2 = ASTQuery.eic(it.getName().getText(), this.genericImport);
                if (_eic_2) {
                  this.importNUOPCGeneric = new NUOPCComponent.GenericImport(this, this.genericImport).reverse();
                }
              }
            }
          };
          Iterables.<ASTUseStmtNode>filter(this._astRef.getModuleBody(), ASTUseStmtNode.class).forEach(_function_1);
          _xblockexpression_1 = this.<NUOPCComponent>reverseChildren();
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = null;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public <T extends NUOPCComponent> T reverseChildren() {
    return ((T) this);
  }
  
  @Override
  public NUOPCComponent forward() {
    try {
      NUOPCComponent _xblockexpression = null;
      {
        boolean _equals = Objects.equal(this.name, null);
        if (_equals) {
          throw new CodeGenerationException("No component name specified");
        }
        final ASTExecutableProgramNode epn = new ASTExecutableProgramNode();
        ASTEmptyProgramNode _aSTEmptyProgramNode = new ASTEmptyProgramNode();
        epn.setEmptyProgram(_aSTEmptyProgramNode);
        IResource _context = this.getContext();
        TokenList _tokenList = new TokenList();
        FortranAST _fortranAST = new FortranAST(((IFile) _context), epn, _tokenList);
        this._ast = _fortranAST;
        final IFortranAST ast = this._ast;
        boolean _equals_1 = Objects.equal(this._astRef, null);
        if (_equals_1) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("module ");
          _builder.append(this.name);
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("use ESMF");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("use NUOPC");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("implicit none");
          _builder.newLine();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("contains");
          _builder.newLine();
          _builder.append("\t");
          _builder.newLine();
          _builder.append("end module");
          _builder.newLine();
          String code = _builder.toString();
          ASTModuleNode moduleNode = CodeExtraction.<ASTModuleNode>parseLiteralProgramUnit(code);
          this.setASTRef(moduleNode);
          ASTListNode<IProgramUnit> pul = new ASTListNode<IProgramUnit>();
          pul.add(moduleNode);
          ASTExecutableProgramNode _root = ast.getRoot();
          _root.setProgramUnitList(pul);
          final Consumer<ASTUseStmtNode> _function = (ASTUseStmtNode it) -> {
            boolean _eic = ASTQuery.eic(it.getName().getText(), "ESMF");
            if (_eic) {
              BasicCodeConcept<ASTUseStmtNode> _basicCodeConcept = new BasicCodeConcept<ASTUseStmtNode>(this, it);
              this.importESMF = _basicCodeConcept;
            } else {
              boolean _eic_1 = ASTQuery.eic(it.getName().getText(), "NUOPC");
              if (_eic_1) {
                BasicCodeConcept<ASTUseStmtNode> _basicCodeConcept_1 = new BasicCodeConcept<ASTUseStmtNode>(this, it);
                this.importNUOPC = _basicCodeConcept_1;
              }
            }
          };
          Iterables.<ASTUseStmtNode>filter(moduleNode.getModuleBody(), ASTUseStmtNode.class).forEach(_function);
        }
        this.importNUOPCGeneric.forward();
        _xblockexpression = super.<NUOPCComponent>forward();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
