package projetoAnimes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimeFlix implements SistemaDeAnimes {

	private List<Anime> animes;
	private GravadorDeAnimes gravador;

	public AnimeFlix() {
		this(new ArrayList<>());
	}

	public AnimeFlix(List<Anime> animes) {
		this.animes = animes;
		this.gravador = new GravadorDeAnimes("src\\Animes");
	}

	public List<Anime> getAnimes() {
		return animes;
	}

	@Override
	public Anime pesquisarAnime(String nomeAnime) throws AnimeException {
		for (Anime a : this.animes) {
			if (a.getNome().equalsIgnoreCase(nomeAnime)) {
				return a;
			}
		}
		throw new AnimeException("ANIME n�o encontrado!");
	}

	@Override
	public Episodio pesquisarEpisodio(String nomeAnime, String tituloEpisodio) throws AnimeException {
		Anime anime = this.pesquisarAnime(nomeAnime);
		for (Episodio ep : anime.getEpisodios()) {
			if (ep.getTitulo().equalsIgnoreCase(tituloEpisodio)) {
				return ep;
			}
		}
		throw new AnimeException("EPIS�DIO n�o encontrado.");
	}

	@Override
	public Episodio pesquisarEpisodio(String nomeAnime, int numeroEpisodio) throws AnimeException {
		Anime anime = this.pesquisarAnime(nomeAnime);
		for (Episodio ep : anime.getEpisodios()) {
			if (ep.getNumero() == numeroEpisodio) {
				return ep;
			}
		}
		throw new AnimeException("EPIS�DIO n�o encontrado.");
	}

	@Override
	public String exibirEpisodio(String nomeAnime, String tituloOuNumeroDoEpisodio) throws AnimeException {
		Anime anime = this.pesquisarAnime(nomeAnime);
		Episodio episodio = null;
		if (!tituloOuNumeroDoEpisodio.equals("") && tituloOuNumeroDoEpisodio.matches("[+-]?\\d*(\\.\\d+)?")) {
			episodio = this.pesquisarEpisodio(nomeAnime, Integer.parseInt(tituloOuNumeroDoEpisodio));
		} else {
			episodio = this.pesquisarEpisodio(nomeAnime, tituloOuNumeroDoEpisodio);
		}
		return "Estamos exibindo o epis�dio " + episodio.getNumero() + " de " + anime.getNome() + ": \""
				+ episodio.getTitulo() + "\". Aproveite bastante cada segundo!";
	}

	@Override
	public void cadastrarEpisodio(String nomeAnime, Episodio episodio) throws AnimeException {
		Anime anime = this.pesquisarAnime(nomeAnime);
		if (episodio.getTitulo().isBlank()) {
			throw new AnimeException("N�o � poss�vel adicionar um epis�dio sem nome.");
		}
		for (Episodio ep : anime.getEpisodios()) {
			if (ep.getNumero() == episodio.getNumero() || ep.getTitulo().equalsIgnoreCase(episodio.getTitulo())) {
				throw new AnimeException("ERRO ao cadastrar! Epis�dio j� existente.");
			}
		}
		anime.getEpisodios().add(episodio);
	}

	@Override
	public void removerEpisodio(String nomeAnime, String tituloOuNumeroDoEpisodio) throws AnimeException {
		Anime anime = this.pesquisarAnime(nomeAnime);
		Episodio episodio = null;
		if (!tituloOuNumeroDoEpisodio.equals("") && tituloOuNumeroDoEpisodio.matches("[+-]?\\d*(\\.\\d+)?")) {
			episodio = this.pesquisarEpisodio(nomeAnime, Integer.parseInt(tituloOuNumeroDoEpisodio));
		} else {
			episodio = this.pesquisarEpisodio(nomeAnime, tituloOuNumeroDoEpisodio);
		}

		if (!anime.getEpisodios().contains(episodio)) {
			throw new AnimeException("Imposs�vel remover esse epis�dio, pois ele n�o existe.");
		}
		anime.getEpisodios().remove(episodio);
	}

	@Override
	public void cadastrarAnime(Anime anime) throws AnimeException {
		if (anime.getNome().isBlank()) {
			throw new AnimeException("N�o � poss�vel cadastrar um anime sem nome.");
		}
		if (this.animes.contains(anime)) {
			throw new AnimeException("N�o foi poss�vel realizar o cadastro! Anime j� existente.");
		}
		this.animes.add(anime);
	}

	@Override
	public void removerAnime(String nomeAnime) throws AnimeException {
		Anime anime = null;
		for (Anime a : this.animes) {
			if (a.getNome().equalsIgnoreCase(nomeAnime)) {
				anime = a;
				break;
			}
		}
		if (anime == null) {
			throw new AnimeException("N�o foi poss�vel remover esse anime, pois ele n�o existe.");
		}
		this.animes.remove(anime);
	}

	@Override
	public void recuperarDados() throws IOException {
		this.animes = this.gravador.recuperarAnimes();
	}

	@Override
	public void salvarDados() throws IOException {
		this.gravador.salvarAnimes(this.animes);
	}

}
