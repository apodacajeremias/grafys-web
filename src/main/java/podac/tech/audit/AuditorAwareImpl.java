package podac.tech.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		String name = "SYSTEM";
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			name = SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return Optional.ofNullable(name);
	}

}
