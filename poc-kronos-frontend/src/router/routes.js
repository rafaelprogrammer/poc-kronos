import AppSidebar from "@/layouts/App/NavDrawer";
import AppToolbar from "@/layouts/App/Toolbar";
import AppFooter from "@/layouts/App/Footer";

import LoginPage from "@/views/LoginPage";
import UsuariosPage from "@/views/usuario/UsuariosPage";
import GerenciaCadastroPessoas from "@/views/pessoa/GerenciaCadastroPessoas";
import GerenciaCadastroCursos from "@/views/curso/GerenciaCadastroCursos";
import GerenciaCadastroDisciplinas from "@/views/disciplina/GerenciaCadastroDisciplinas";
import GerenciaCadastroMatriculas from "@/views/matricula/GerenciaCadastroMatriculas";
import GerenciaCadastroContratos from "@/views/contrato/GerenciaCadastroContratos";
import GerenciaCadastroCalendario from "@/views/calendario/GerenciaCadastroCalendario";
import GerenciaCadastroHorarios from "@/views/horario/GerenciaCadastroHorarios";
import GerenciaCadastroEstruturaAnoLetivo from "@/views/estruturaanoletivo/GerenciaCadastroEstruturaAnoLetivo";
import GerenciaCadastroEstrutraPedagogica from "@/views/estruturapedagogica/GerenciaCadastroEstrutraPedagogica";
import GerenciaCadastroPlanoPedagogico from "@/views/planopedagogico/GerenciaCadastroPlanoPedagogico";
import GerenciaCadastroDiario from "@/views/diario/GerenciaCadastroDiario";
//import GerenciaCadastroFrequencia from "@/views/frequencia/GerenciaCadastroFrequencia";
import GerenciaCadastroAvaliacao from "@/views/avaliacao/GerenciaCadastroAvaliacao";
import GerenciaResultadoAvaliacaoPorHabilidade from "@/views/avaliacao/GerenciaResultadoAvaliacaoPorHabilidade";
import GerenciaCadastroTiposOcorrencias from "@/views/ocorrencia/GerenciaCadastroTiposOcorrencias";
import GerenciaCadastroOcorrencias from "@/views/ocorrencia/GerenciaCadastroOcorrencias";
import GerenciaCadastroAtestados from "@/views/atestado/GerenciaCadastroAtestados";
import GerenciaCadastroFuncionario from "@/views/funcionario/GerenciaCadastroFuncionario";
import GerenciaCadastroPontos from "@/views/funcionario/GerenciaCadastroPontos";
import GerenciaCadastroHomologacaoPonto from "@/views/funcionario/GerenciaCadastroHomologacaoPonto";
import GerenciaRelatorioPedagogico from "@/views/relatorio_pedagogico/GerenciaRelatorioPedagogico";
import GerenciaRelatorioAlunos from "@/views/relatorio_alunos/GerenciaRelatorioAlunos";
import GerenciaResultadoBimestre from "@/views/curso/subfase/GerenciaResultadoBimestre";

import Home from "@/views/Home";

export const routes = [
  {
    path: "/",
    name: "Home",
    components: {
      default: Home,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/login",
    name: "LoginPage",
    components: {
      default: LoginPage
    }
  },
  {
    path: "/usuarios",
    name: "UsuariosPage",
    components: {
      default: UsuariosPage,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-pessoas",
    name: "GerenciaCadastroPessoas",
    components: {
      default: GerenciaCadastroPessoas,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-cursos",
    name: "GerenciaCadastroCursos",
    components: {
      default: GerenciaCadastroCursos,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-disciplinas",
    name: "GerenciaCadastroDisciplinas",
    components: {
      default: GerenciaCadastroDisciplinas,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-matriculas",
    name: "GerenciaCadastroMatriculas",
    components: {
      default: GerenciaCadastroMatriculas,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-contratos",
    name: "GerenciaCadastroContratos",
    components: {
      default: GerenciaCadastroContratos,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-calendario",
    name: "GerenciaCadastroCalendario",
    components: {
      default: GerenciaCadastroCalendario,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-horario",
    name: "GerenciaCadastroHorarios",
    components: {
      default: GerenciaCadastroHorarios,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-estrutra-ano-letivo",
    name: "GerenciaCadastroEstruturaAnoLetivo",
    components: {
      default: GerenciaCadastroEstruturaAnoLetivo,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-estrutra-pedagogica",
    name: "GerenciaCadastroEstrutraPedagogica",
    components: {
      default: GerenciaCadastroEstrutraPedagogica,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-plano-pedagogico",
    name: "GerenciaCadastroPlanoPedagogico",
    components: {
      default: GerenciaCadastroPlanoPedagogico,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-diario",
    name: "GerenciaCadastroDiario",
    components: {
      default: GerenciaCadastroDiario,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  // {
  //   path: "/gerencia-frequencia",
  //   name: "GerenciaCadastroFrequencia",
  //   components: {
  //     default: GerenciaCadastroFrequencia,
  //     sidebar: AppSidebar,
  //     header: AppToolbar,
  //     footer: AppFooter
  //   }
  // },
  {
    path: "/gerencia-avaliacao",
    name: "GerenciaCadastroAvaliacao",
    components: {
      default: GerenciaCadastroAvaliacao,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-resultado-avaliacao",
    name: "GerenciaResultadoAvaliacaoPorHabilidade",
    components: {
      default: GerenciaResultadoAvaliacaoPorHabilidade,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-tipos-ocorrencias",
    name: "GerenciaCadastroTiposOcorrencias",
    components: {
      default: GerenciaCadastroTiposOcorrencias,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-ocorrencias",
    name: "GerenciaCadastroOcorrencias",
    components: {
      default: GerenciaCadastroOcorrencias,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-atestados",
    name: "GerenciaCadastroAtestados",
    components: {
      default: GerenciaCadastroAtestados,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-funcionarios",
    name: "GerenciaCadastroFuncionario",
    components: {
      default: GerenciaCadastroFuncionario,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-pontos",
    name: "GerenciaCadastroPontos",
    components: {
      default: GerenciaCadastroPontos,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-homologacao-pontos",
    name: "GerenciaCadastroHomologacaoPonto",
    components: {
      default: GerenciaCadastroHomologacaoPonto,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-relatorios-pedagogicos",
    name: "GerenciaRelatorioPedagogico",
    components: {
      default: GerenciaRelatorioPedagogico,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-relatorios-alunos",
    name: "GerenciaRelatorioAlunos",
    components: {
      default: GerenciaRelatorioAlunos,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  },
  {
    path: "/gerencia-resultados-bimestres",
    name: "GerenciaResultadoBimestre",
    components: {
      default: GerenciaResultadoBimestre,
      sidebar: AppSidebar,
      header: AppToolbar,
      footer: AppFooter
    }
  }
];
