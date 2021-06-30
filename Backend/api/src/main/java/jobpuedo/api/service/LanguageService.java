package jobpuedo.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobpuedo.api.entity.Language;
import jobpuedo.api.entity.User;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.repository.ILanguageRepository;

@Service
public class LanguageService {

	@Autowired
	private ILanguageRepository languageRepository;

	public Language findById(int id) {
		Optional<Language> op = languageRepository.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new NoExistsResourceException("El idioma solicitado no existe.");
	}

	public Language add(Language language, User user) {
		language.setUser(user);
		return this.save(language);
	}

	public Language edit(int id, Language language) {
		Language oldLanguage = this.findById(id);
		oldLanguage.setLevel(language.getLevel());
		oldLanguage.setName(language.getName());
		return this.save(oldLanguage);
	}

	public User delete(int id) {
		Language language = this.findById(id);
		User user = language.getUser();
		languageRepository.delete(language);
		return user;
	}

	public Language save(Language language) {
		return languageRepository.save(language);
	}

}
