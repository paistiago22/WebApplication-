package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Controller // This means that this class is a Controller
public class MainController {
    @Autowired 
	private NoticiasRepository notRepository;
    
    @Autowired 
	private ConsumidorRepository conRepository;
    
    @Autowired 
	private ProdutorRepository proRepository;
    
    @Autowired 
	private TopicosRepository topRepository;
    
    @Autowired 
  	private FavoritosRepository favRepository;
    
    private Produtor curUser;
    private Consumidor curConsumidor;
    Noticias notList;
    Noticias notList1;
    int y=0;
    ArrayList<Noticias> a = new ArrayList<>();
    ArrayList<NoticiaList> aa = new ArrayList<>();
    ArrayList<NoticiaList> bb = new ArrayList<>();

    @GetMapping(path="/")
	public String getAllUsers1 (Model model) {
    	model.addAttribute("ListProdutor" , proRepository.findAll()); //ListUsers fica accessivel desde o .html	
		model.addAttribute("ListTopics", topRepository.findAll());
		model.addAttribute("ListNoticias", notRepository.findAll());
		model.addAttribute("ListConsumidor", conRepository.findAll());
		return "inicial";
	}
    
    @GetMapping(path="/prodotor")
	public String getAllUsers (Model model) {
		
		//print(userRepository.count());
		return "index";
	}	
    
	
    @GetMapping("/add_Produtor")
	public String add_Produtor(Model model) {
		// create model attribute to bind form data
    	System.out.println("CHEGOU AQUI");
    	Produtor p = new Produtor();
    	model.addAttribute("newprodutor", p);
		return "adduser";
	}
    
    
    
    @PostMapping("/saveProdutor")
	public String saveProdutor (@ModelAttribute("newprodutor") Produtor produtor, @RequestParam("nome_produtor") String nome_produtor ) {

    	if(nome_produtor != "") {

	    	produtor.setId_produtor((int)proRepository.count()+1);
	    	produtor.setNome_produtor(nome_produtor);
	    	System.out.println(produtor.toString());
			boolean i = true;
			for(Produtor t: proRepository.findAll()) {
				if(t.getNome_produtor().equals(produtor.getNome_produtor())) {
					i = false;
				}
			}
			
			if(i) {
				proRepository.save(produtor);
				return "redirect:/prodotor";
			} else {
				return "redirect:/add_Produtor";
			}
    	}
    	return "adduser";
	}
    
    
    
    
    @PostMapping("/loginUser")
	public String loginUser(Model model, @ModelAttribute("loginuser") LoginUser user, @RequestParam("lognome") String nome) {
		
		
		Iterable<Produtor> users = proRepository.findAll();

		if(!nome.equals("")) {
			for(Produtor u : users) {
				if(u.getNome_produtor().equals(nome)) {
					
						curUser = new Produtor();
						curUser.setId_produtor(u.getId_produtor());
						curUser.setNome_produtor(u.getNome_produtor());
															
						return "redirect:loadProdutorPage";
					 
				} 
			}
		}
		
		return "redirect:/prodotor";
	}
	
	
	@GetMapping("/loadProdutorPage")
	public String loadUserRole(Model model) {
		
		model.addAttribute("curUser", curUser);
		return "pag_produtor";	
	}
	
	@GetMapping("/lista_topicos")
	public String listTopics(Model model) {
		 model.addAttribute("ListTopics", topRepository.findAll());
		 return "lista_de_topicos";
	}
	
	
	@GetMapping("/novo_topico")
	public String addNewTopic(Model model) {

		 Topicos topic = new Topicos();
		 model.addAttribute("newtopic", topic);
		 return "nov_top";
	}
	
	@PostMapping("/saveTopic")
	public String saveDep (@ModelAttribute("newtopic") Topicos topic ) {

		topic.setId_topico((int)topRepository.count()+1);
		boolean h = true;
		for(Topicos t: topRepository.findAll()) {
			if(t.getNome_topico().equals(topic.getNome_topico())) {
				h = false;
			}
		}
		
		if(h) {
			topRepository.save(topic);
			return "redirect:/loadProdutorPage";
		} else {
			return "redirect:/novo_topico";
		}
	
	}
	
	
	
	
	@GetMapping("/nova_noticia")
	public String addNewNoticia(Model model) {
		 ArrayList<Topicos> op = new ArrayList<Topicos>();
	     for(Topicos t: topRepository.findAll()) {
	    	 op.add(t);
	     }
	     
	     model.addAttribute("op", op);
		 
		 Noticias noticia = new Noticias();
		 model.addAttribute("newnoticia", noticia);
		 return "nov_noti";
	}
	
	@PostMapping("/saveNoticia")
	public String saveNoticia (@ModelAttribute("newtopic") Noticias noticia ) {
	 // save dep to database
		noticia.setId_noticia((int)notRepository.count()+1); //id da noticia
		noticia.setId_produtor(curUser.getId_produtor()); //id do escritor
		
		LocalDateTime data = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dt = data.format(formatter);
		noticia.setData_publicacao(dt);
		
		
		notRepository.save(noticia);
		return "redirect:/loadProdutorPage";
	
	}
	
	

	@GetMapping("/lista_Noticias")
	public String listNoticias(Model model) {
		 Iterable<Noticias> noticias = notRepository.findAll();
		 Iterable<Produtor> users =proRepository.findAll();
		 Iterable<Topicos> users1 =topRepository.findAll();
		 ArrayList<NoticiaList> notList = new ArrayList<>();
		 String h="";
		 
		 
		 String curName = "";
		 String data = "";
		 for(Noticias n: noticias) {
			 
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			 data = n.getData_publicacao();
			 
			 for(Produtor u : users) {
				 if(u.getId_produtor() == n.getId_produtor()) {
					 curName = u.getNome_produtor();
				 }
			 }
			 
			 for(Topicos u : users1) {
				 if(u.getId_topico() == n.getId_topico()) { 
					 h = u.getNome_topico();
				 }
			 }
			 notList.add(new NoticiaList(n.getId_noticia(),n.getTitulo(),h, n.getCorpo_noticia(), data, curName));
		 }
		
		 model.addAttribute("ListNoticias", notList);
		 //model.addAttribute("ListNoticias", noticiaRepository.findAll());
		 
		 return "lis_not";
	}
	
	
	
	//CONSUMIDOR
	
	
	@GetMapping(path="/consumidor")
	public String getAllCon (Model model) {

		return "consumidor_login";
	}	
	
	
	
	 @GetMapping("/add_Consumidor")
	public String add_Consumidor(Model model) {
		// create model attribute to bind form data
    	System.out.println("CHEGOU AQUI");
    	Consumidor p = new Consumidor();
    	model.addAttribute("newconsumidor", p);
		return "addCon";
	}

			 
	@PostMapping("/saveConsumidor")
			public String saveConsumidor (Model model, @ModelAttribute("newconsumidor") Consumidor consumidor, @RequestParam("nome_consumidor") String nome, @RequestParam("morada") String morada, @RequestParam("contacto") int contacto,@RequestParam("password") String password) {

				System.out.println("sdpfspdf");
				if(!nome.equals("") && !morada.equals("") && !password.equals("") && contacto != 0) {
					
					System.out.println("sdpfspdf");
				
					consumidor.setId_consumidor((int)conRepository.count()+1);
					consumidor.setMorada(morada);
					consumidor.setNome_consumidor(nome);
					consumidor.setPassword(password);
					consumidor.setContacto(contacto);
				    System.out.println(consumidor.toString());
					boolean i = true;
					
					for(Consumidor t: conRepository.findAll()) {
						if(t.getNome_consumidor().equals(consumidor.getNome_consumidor())) {
							i = false;
						}
					}
						
					if(i) {
						conRepository.save(consumidor);
						return "redirect:/consumidor";
					} else {
						return "redirect:/add_Produtor";
					}			
				}	
				return "addCon";
		}
		 
		 
	 @PostMapping("/loginCon")
		public String loginCon(Model model, @ModelAttribute("newconsumidor") Consumidor consumidor, @RequestParam("lognome") String nome, @RequestParam("logpass") String pass) {
			
			
			Iterable<Consumidor> users = conRepository.findAll();

			if(!nome.equals("") || pass.equals("")) {
				for(Consumidor u : users) {
					if(u.getNome_consumidor().equals(nome) && u.getPassword().equals(pass)) {
							System.out.println("CHEGOU AQUI");
							curConsumidor = new Consumidor();
							curConsumidor.setNome_consumidor(u.getNome_consumidor());
							curConsumidor.setId_consumidor(u.getId_consumidor());
							curConsumidor.setContacto(u.getContacto());
							curConsumidor.setMorada(u.getMorada());
																
							return "redirect:loadConsumidorRole";
						 
					} 
				}
			}
			//return "redirect:/loginCon";
			return "consumidor_login";
		}
	 	 
	 @GetMapping("/loadConsumidorRole")
		public String loadConsumidorRole(Model model) {
			
			model.addAttribute("curConsumidor", curConsumidor);
			return "pag_consumidor";	
		}
	 
	 @GetMapping("/este")
		public String este(Model model) {
			
			model.addAttribute("curConsumidor", curConsumidor);
			return "pag_consumidor";	
		}
	 
	 @GetMapping("/esteSemRegisto")
		public String esteSemRegisto(Model model) {
			
			model.addAttribute("curConsumidor", curConsumidor);
			return "pag_consumidor_sem_registo";	
		}
	 
	 @GetMapping("/UltimaNoticia")
		public String UltimaNoticia(Model model) {
			 ArrayList<Topicos> op = new ArrayList<Topicos>();
		     for(Topicos t: topRepository.findAll()) {
		    	 op.add(t);
		     }
		     model.addAttribute("op", op);
			 Noticias noticia = new Noticias();
			 model.addAttribute("newnoticia", noticia);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "Ultimo_topico";	
		}
	 
	 
	 @PostMapping("/getTopico")
		public String getTopico (@ModelAttribute("newtopic") Noticias noticia ) {
		 // save dep to database
		 	System.out.println(noticia.getId_topico());
		 	y=0;
		 	
		 	notList = new Noticias();
		 	notList.setData_publicacao("1200-01-03 12:10:48");
		 	for(Noticias t: notRepository.findAll()) {
		    	 if(t.getId_topico() == noticia.getId_topico()) {
		    		 LocalDateTime auxData = LocalDateTime.parse(t.getData_publicacao(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		    		 if(auxData.isAfter(LocalDateTime.parse(notList.getData_publicacao(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))){
		    			 notList = t;
		    		 }
		    		 y++;
		    	 }
		     }
		 	
		 
		 	System.out.println(notList.toString());

			return "redirect:/Ver_o_Topico";

		}
	 
	 
	 @GetMapping("/Ver_o_Topico")
		public String Ver_o_Topico(Model model) {
		 
		 	Noticias d = new Noticias();
		 	d.setCorpo_noticia("Nao existe Noticias deste Topico");
		 	d.setTitulo("-----------------------");
		 	d.setData_publicacao("----------");
		 
		 	if(y==0) {
		 		model.addAttribute("Noticia", d);
		 	}else {
		 		model.addAttribute("Noticia", notList);
		 	}
		 	
			return "Ver_O_Topico";
		}
	 
	 
	 @GetMapping("/DataNoticia")
		public String DataNoticia(Model model) {
			 ArrayList<Topicos> op = new ArrayList<Topicos>();
		     for(Topicos t: topRepository.findAll()) {
		    	 op.add(t);
		     }
		     model.addAttribute("op", op);
			 Noticias noticia = new Noticias();
			 model.addAttribute("newnoticia", noticia);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "Intervalo_de_Datas";	
		} 
	 
	 
	 @PostMapping("/getD")
		public String getD (@ModelAttribute("newtopic") Noticias noticia,@RequestParam("data1") String nome,@RequestParam("data2") String nome1) {
		 	
		 
		 	if(nome.equals("") || nome1.equals("")) {
		 		return "redirect:/este";
		 	}
		 	else {

			 	a = new ArrayList<>();
			 	aa = new ArrayList<>();
			 	/*
			 		nome = nome + " 00:00:00";
			 		nome1 = nome1 + " 00:00:00";
			 	 */
			 	
			 	 
			 	  	LocalDateTime data1 = LocalDateTime.now();
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
					String dt1 = data1.format(formatter1);
			 	 
			 	 	nome = nome + " "+dt1;
			 		nome1 = nome1 + " " +dt1; 
			 	 
			
			 	
			 	
			 	for(Noticias t: notRepository.findAll()) {
			    	 if(t.getId_topico() == noticia.getId_topico()) {
			    		 System.out.println(t.getData_publicacao());
			    		 
			    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			    		 LocalDateTime dateTime2 = LocalDateTime.parse(t.getData_publicacao(), formatter);
			    		 LocalDateTime dateTime = LocalDateTime.parse(nome, formatter);
			    		 LocalDateTime dateTime1 = LocalDateTime.parse(nome1, formatter); 
			    		 	
			    		 
			    		 if(dateTime2.isAfter(dateTime) && dateTime2.isBefore(dateTime1)){
			    			 for(Produtor p : proRepository.findAll()) {
			    				 if(p.getId_produtor() == t.getId_produtor()) {
			    					 aa.add(new NoticiaList(t.getId_noticia(),t.getTitulo(),"q", t.getCorpo_noticia(), t.getData_publicacao(), p.getNome_produtor()));
			    				 }
			    			 }
			    			 a.add(t);
			    		 }
			    		 
			    	 }
			 	}
		 	
			 	System.out.println(a.toString());

			 	return "redirect:/Ver_o";
		 	}
		}
	 
	 
	 @GetMapping("/Ver_o")
		public String Ver_o(Model model) {
		 
		 	NoticiaList d = new NoticiaList();
		 	d.setConteudo("Nao existe Noticias deste Topico neste Intervalo de Datas");
		 	d.setTitulo("-----------------------");
		 	d.setData("----------");
		 	d.setProdutor("------------");
		 	d.setId(0);
		 	d.setTopico("------");
		 
		 	if(aa.size()==0) {
		 		model.addAttribute("Noticia", d);
		 	}else {
		 		model.addAttribute("Noticia", aa);
		 	}
			return "Intervalo";
		}
	 
	 
	 
	 @GetMapping("/VerOsTopicosFavoritos")
		public String FavoritoFavoritos(Model model) {
			 ArrayList<Topicos> op = new ArrayList<Topicos>();
		     for(Topicos t: topRepository.findAll()) {
		    	 boolean h = false;
		    	 for(Favoritos p : favRepository.findAll()	) {
		    		 if(p.getId_consumidor() == curConsumidor.getId_consumidor()) {
		    			 
		    		 
		    			 if(p.getTopico() == t.getId_topico()) {
		    				 h=true;
		    			 }
		    		 }	 
		    	 }
		    	 if(h) {
		    		 op.add(t);
		    	 }
		     }
		     
		     model.addAttribute("op", op);
			 Noticias noticia = new Noticias();
			 model.addAttribute("newnoticia", noticia);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "NotFavRemover";	
		}
	 
	 
	 @PostMapping("/getRemFav")
		public String getRemFav (@ModelAttribute("newtopic") Noticias noticia) {
		 
		 
		 for(Favoritos f : favRepository.findAll()) {
			 if(f.getTopico() == noticia.getId_topico()) {
				 favRepository.deleteById(f.getId_fav());
			 }
		 }

		 return "redirect:/este";
		 	
		}
	 
	 
	 
	 @GetMapping("/VerOsTopicos")
		public String Favorito(Model model) {
			 ArrayList<Topicos> op = new ArrayList<Topicos>();
		     for(Topicos t: topRepository.findAll()) {
		    	 boolean h = false;
		    	 for(Favoritos p : favRepository.findAll()	) {
		    		 if(p.getId_consumidor() == curConsumidor.getId_consumidor()) {
		    			 
		    		 
		    			 if(p.getTopico() == t.getId_topico()) {
		    				 h=true;
		    			 }
		    		 }	 
		    	 }
		    	 if(!h) {
		    		 op.add(t);
		    	 }
		     }
		     
		     model.addAttribute("op", op);
			 Noticias noticia = new Noticias();
			 model.addAttribute("newnoticia", noticia);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "NotFav";	
		} 
	 
	 
	 @PostMapping("/getNewFav")
		public String getNewFav (@ModelAttribute("newtopic") Noticias noticia) {
		 	
		 Favoritos p = new Favoritos();
		 p.setId_consumidor(curConsumidor.getId_consumidor());
		 p.setId_fav((int)favRepository.count()+1);
		 p.setTopico(noticia.getId_topico());
		 
		 favRepository.save(p);	
		 
		 return "redirect:/este";
		 	
		}
	 
	
	 @GetMapping("/MostrarNoticiasFav")
		public String MostrarNoticiasFav(Model model) {
		 ArrayList<Noticias> f= new ArrayList<>();
		 bb = new ArrayList<>();
		     for(Noticias a : notRepository.findAll()) {
		    	 for(Favoritos p : favRepository.findAll()) {
		    		 if(p.getId_consumidor()==curConsumidor.getId_consumidor()) {
		    			 if(a.getId_topico() == p.getTopico()) {
		    				 f.add(a);
		    			 }
		    		 }
		    	 } 
		     }
		     
		     String h="",produtor="";
		     for(Noticias a : f) {
		    	 for(Produtor p : proRepository.findAll()) {
		    		 if(a.getId_produtor() == p.getId_produtor()) {
		    			 h = p.getNome_produtor();
		    		 }
		    	 }
		    	 
		    	 for(Topicos pp : topRepository.findAll()) {
		    		 if(a.getId_topico() == pp.getId_topico()) {
		    			 produtor = pp.getNome_topico();
		    		 }
		    	 }
		    	 
		    	 bb.add(new NoticiaList(a.getId_noticia(),a.getTitulo(),produtor, a.getCorpo_noticia(), a.getData_publicacao(),h));
		    	 
		     }
		     Collections.reverse(bb);
		     System.out.println(bb);
		     
		     if(bb.size()==0) {
		    	 NoticiaList p = new NoticiaList(-1,"Nao existe Noticias destes Topicos","-----------","-----------","-----------","-----------");
		    	 bb.add(p);
		     }
		     
			 model.addAttribute("newnoticia", bb);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "MostarNoticiasFav";	
		} 
	 
	 
	 
	 
	 
	 
	 // asdasdadasdasdsad
	 
	 
	 @GetMapping("/UltimaNoticia1")
		public String UltimaNoticia1(Model model) {
			 ArrayList<Topicos> op = new ArrayList<Topicos>();
		     for(Topicos t: topRepository.findAll()) {
		    	 op.add(t);
		     }
		     model.addAttribute("op", op);
			 Noticias noticia = new Noticias();
			 model.addAttribute("newnoticia", noticia);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "UltimoTopico_SemRegisto";	
		}
	 
	 
	 @PostMapping("/getTopico1")
		public String getTopico1 (@ModelAttribute("newtopic") Noticias noticia ) {
		 // save dep to database
		 	System.out.println(noticia.getId_topico());
		 	System.out.println("SDFSDFSDF");
		 	y=0;
		 	
		 	notList = new Noticias();
		 	notList.setData_publicacao("1200-01-03 12:10:48");
		 	for(Noticias t: notRepository.findAll()) {
		    	 if(t.getId_topico() == noticia.getId_topico()) {
		    		 LocalDateTime auxData = LocalDateTime.parse(t.getData_publicacao(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		    		 if(auxData.isAfter(LocalDateTime.parse(notList.getData_publicacao(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))){
		    			 notList = t;
		    		 }
		    		 y++;
		    	 }
		     }
		 	
		 
		 	System.out.println(notList.toString());

			return "redirect:/Ver_o_Topico1";

		}
	 
	 
	 @GetMapping("/Ver_o_Topico1")
		public String Ver_o_Topico1(Model model) {
		 
		 	Noticias d = new Noticias();
		 	d.setCorpo_noticia("Nao existe Noticias deste Topico");
		 	d.setTitulo("-----------------------");
		 	d.setData_publicacao("----------");
		 
		 	if(y==0) {
		 		model.addAttribute("Noticia", d);
		 	}else {
		 		model.addAttribute("Noticia", notList);
		 	}
		 	
			return "Ver_SemRegisto";
		}
	 
	 
	 
	 @GetMapping("/DataNoticia1")
		public String DataNoticia1(Model model) {
			 ArrayList<Topicos> op = new ArrayList<Topicos>();
		     for(Topicos t: topRepository.findAll()) {
		    	 op.add(t);
		     }
		     model.addAttribute("op", op);
			 Noticias noticia = new Noticias();
			 model.addAttribute("newnoticia", noticia);
			 model.addAttribute("curConsumidor", curConsumidor);
			 return "Intervalo_de_Datas_Sem";	
		} 
	 
	 
	 @PostMapping("/getD1")
		public String getD1 (@ModelAttribute("newtopic") Noticias noticia,@RequestParam("data1") String nome,@RequestParam("data2") String nome1) {
		 	
		 
		 	if(nome.equals("") || nome1.equals("")) {
		 		return "redirect:/esteSemRegisto";
		 	}
		 	else {

			 	a = new ArrayList<>();
			 	aa = new ArrayList<>();
			 	
			 	nome = nome + " 00:00:00";
			 	nome1 = nome1 + " 00:00:00";
	
			 	
			 	for(Noticias t: notRepository.findAll()) {
			    	 if(t.getId_topico() == noticia.getId_topico()) {
			    		 System.out.println(t.getData_publicacao());
			    		 
			    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			    		 LocalDateTime dateTime2 = LocalDateTime.parse(t.getData_publicacao(), formatter);
			    		 LocalDateTime dateTime = LocalDateTime.parse(nome, formatter);
			    		 LocalDateTime dateTime1 = LocalDateTime.parse(nome1, formatter); 
			    		 	
			    		 
			    		 if(dateTime2.isAfter(dateTime) && dateTime2.isBefore(dateTime1)){
			    			 for(Produtor p : proRepository.findAll()) {
			    				 if(p.getId_produtor() == t.getId_produtor()) {
			    					 aa.add(new NoticiaList(t.getId_noticia(),t.getTitulo(),"q", t.getCorpo_noticia(), t.getData_publicacao(), p.getNome_produtor()));
			    				 }
			    			 }
			    			 a.add(t);
			    		 }
			    		 
			    	 }
			 	}
		 	
			 	System.out.println(a.toString());

			 	return "redirect:/Ver_o1";
		 	}
		}
	 
	 
	 @GetMapping("/Ver_o1")
		public String Ver_o1(Model model) {
		 
		 	NoticiaList d = new NoticiaList();
		 	d.setConteudo("Nao existe Noticias deste Topico neste Intervalo de Datas");
		 	d.setTitulo("-----------------------");
		 	d.setData("----------");
		 	d.setProdutor("------------");
		 	d.setId(0);
		 	d.setTopico("------");
		 
		 	if(aa.size()==0) {
		 		model.addAttribute("Noticia", d);
		 	}else {
		 		model.addAttribute("Noticia", aa);
		 	}
			return "Intervalo1";
		}
	 	
}