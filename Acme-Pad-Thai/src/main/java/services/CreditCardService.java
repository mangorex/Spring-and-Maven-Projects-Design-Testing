package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Sponsor;

@Transactional
@Service
public class CreditCardService {
	// Managed repository -------------------------------

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private SponsorService sponsorService;

	// Constructor --------------------------------------
	public CreditCardService() {
		super();

	}

	// Simple CRUD methods ------------------------------

	public CreditCard create(Sponsor s) {
		CreditCard b = new CreditCard();
		b.setSponsor(s);
		b.setExpirationMonth(0);
		b.setExpirationYear(0);
		b.setCodeCVV(0);

		return b;
	}

	public CreditCard save(CreditCard c) {
		Assert.notNull(c);
		// checkPrincipal(c);

		Sponsor sponsor = c.getSponsor();
		Collection<CreditCard> ccs = sponsor.getCreditCards();
		ccs.add(c);
		sponsor.setCreditCards(ccs);
		
		sponsorService.save(sponsor);

		return creditCardRepository.save(c);
	}

	public void delete(CreditCard c) {
		// checkPrincipal(c);
		Sponsor sponsor = c.getSponsor();
		Collection<CreditCard> ccs = sponsor.getCreditCards();
		ccs.remove(c);
		sponsor.setCreditCards(ccs);
		sponsorService.save(sponsor);
		creditCardRepository.delete(c);
	}

	public Collection<CreditCard> findAll() {
		return creditCardRepository.findAll();
	}

	// Other business methods ---------------------------

	public void checkPrincipal(CreditCard c) {
		Sponsor sponsor = sponsorService.findByPrincipal();
		Assert.isTrue(sponsor.equals(c.getSponsor()));
	}
	
	public Collection<CreditCard> findByPrincipal()
	{
		Sponsor s=sponsorService.findByPrincipal();
		return creditCardRepository.findAllBySponsor(s.getId());
	}
	
	public CreditCard findOne(Integer id)
	{
		return creditCardRepository.findOne(id);
	}
}