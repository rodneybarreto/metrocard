package br.uece.metrocard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.uece.metrocard.domain.entity.Account;
import br.uece.metrocard.repository.AccountRepository;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public List<Account> GetAll(){
		return accountRepository.findAll();
	}

	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Account> GetById(@PathVariable(value = "id") int id){

		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent()) 
			return new ResponseEntity<Account>(account.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public void POST(@RequestBody Account account){
		accountRepository.save(account);
	}

	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Account> PUT(@PathVariable(value = "id") int id, @RequestBody Account newAccount){

		Optional<Account> oldAccount = accountRepository.findById(id);
		if (oldAccount.isPresent()) {

			Account account =  oldAccount.get();
			account.setOwner(newAccount.getOwner());
			account.setBalance(newAccount.getBalance());
			accountRepository.save(account);
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
		else
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
	}
	

	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") int id)
	{
		Optional<Account> account = accountRepository.findById(id);
		if(account.isPresent()){
			accountRepository.delete(account.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}