package projetoAnimes;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ProgramaAnimes {

	public static void main(String[] args) {

		AnimeFlix af = new AnimeFlix();

		try {
			af.recuperarDados();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		JOptionPane.showMessageDialog(null, "Seja bem vindo ao AnimeFlix!");

		String opcao = "";
		do {
			opcao = JOptionPane.showInputDialog("Escolha o que deseja fazer: \n"
					+ "1- Adicionar anime\n2- Adicionar epis�dio\n3- Listar animes dispon�veis\n"
					+ "4- Listar epis�dios dispon�veis de um anime\n5- Assitir um epis�dio\n"
					+ "6- Remover anime\n7- Remover epis�dio\n8- Sair");

			switch (opcao) {
			case "1":
				String nome = JOptionPane.showInputDialog("Informe o nome do anime").toUpperCase();
				Anime novoAnime = new Anime(nome, new ArrayList<>());
				try {
					af.cadastrarAnime(novoAnime);
					JOptionPane.showMessageDialog(null, "Anime adicionando com sucesso!");
				} catch (AnimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				break;

			case "2":
				try {
					String nomeAnime = JOptionPane
							.showInputDialog("Informe o nome do anime que deseja adicionar o epis�dio");
					int numeroEpisodio = Integer.parseInt(JOptionPane.showInputDialog("Informe o n�mero do epis�dio"));
					String tituloEpisodio = JOptionPane.showInputDialog("Informe o t�tulo do epis�dio");
					double duracaoEpisodio = Double
							.parseDouble(JOptionPane.showInputDialog("Informe a dura��o do epis�dio"));
					Episodio novoEpisodio = new Episodio(numeroEpisodio, tituloEpisodio, duracaoEpisodio);
					af.cadastrarEpisodio(nomeAnime, novoEpisodio);
					JOptionPane.showMessageDialog(null, "Epis�dio adicionando com sucesso!");
				} catch (AnimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Dados inv�lidos! Na pr�xima vez preencha os campos corretamente.");
				}
				break;

			case "3":
				if (af.getAnimes().size() == 0) {
					JOptionPane.showMessageDialog(null, "Ainda n�o h� registros de animes!");
				} else {
					String todosAnimes = "";
					for (Anime a : af.getAnimes()) {
						todosAnimes += a.toString() + "\n";
					}
					JOptionPane.showMessageDialog(null, todosAnimes);
				}
				break;

			case "4":
				String nomeDoAnime = JOptionPane.showInputDialog("Informe o nome do anime");
				try {
					Anime animeDosEpisodiosBuscados = af.pesquisarAnime(nomeDoAnime);
					String todosEpisodios = "";
					for (Episodio ep : animeDosEpisodiosBuscados.getEpisodios()) {
						todosEpisodios += ep.toString() + "\n";
					}
					if (todosEpisodios.equals("")) {
						JOptionPane.showMessageDialog(null, "Ainda n�o h� epis�dios dispon�veis para esse anime!");
					} else {
						JOptionPane.showMessageDialog(null, todosEpisodios);
					}
				} catch (AnimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				break;

			case "5":
				String nomeDoAnimeEscolhido = JOptionPane.showInputDialog("Informe o nome do anime");
				String tituloOuNumeroDoEpisodioEscolhido = JOptionPane
						.showInputDialog("Informe o titulo ou o n�mero do epis�dio escolhido");
				try {
					JOptionPane.showMessageDialog(null,
							af.exibirEpisodio(nomeDoAnimeEscolhido, tituloOuNumeroDoEpisodioEscolhido));
				} catch (AnimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				break;

			case "6":
				String nomeAnimeParaRemover = JOptionPane.showInputDialog("Informe o nome do anime a ser removido");
				try {
					af.removerAnime(nomeAnimeParaRemover);
					JOptionPane.showMessageDialog(null, "Anime removido com sucesso!");
				} catch (AnimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				break;

			case "7":
				String nomeDoAnimeParaRemoverEpisodio = JOptionPane
						.showInputDialog("Informe o nome do anime que deseja remover um epis�dio");
				String tituloEpisodioParaRemover = JOptionPane
						.showInputDialog("Informe o n�mero ou o t�tulo do epis�dio a ser removido");
				try {
					af.removerEpisodio(nomeDoAnimeParaRemoverEpisodio, tituloEpisodioParaRemover);
					JOptionPane.showMessageDialog(null, "Epis�dio removido com sucesso!");
				} catch (AnimeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				break;

			case "8":
				try {
					af.salvarDados();
					JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				break;
			default:
				JOptionPane.showMessageDialog(null, "Op��o inv�lida! Tente novamente.");
			}
		} while (!opcao.equals("8"));
		JOptionPane.showMessageDialog(null, "Obrigado por utilizar nossos servi�os! At� a pr�xima.");
	}

}
