<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>
	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">
			<!-- Barra de navega��o abaixo da foto -->
			<jsp:include page="navbar.jsp"></jsp:include>
			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">
					<!-- menu de navega��o abaixo da foto -->
					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start/ onde est� a estrutura -->
						<jsp:include page="pag-hedar.jsp"></jsp:include>
						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cad. Usu�rio</h4>
														<!-- enctype="multipart/form-data" ele prepara o formulario para o upload da foto -->
														<!-- enctype="multipart/form-data" ele tamb�m define o tipo de dados que vai vim do formulario -->
														<form class="form-material" enctype="multipart/form-data" action="<%=request.getContextPath()%>/ServletLoginUsuarioController" method="post" id="formUser">
															<input type="hidden" name="acao" id="acao" value="">
														
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                            
                                                            <!-- Campo de imagem do usu�rio -->
                                                            <div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																	    <c:if test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}">
																	    	<a href="<%= request.getContextPath()%>/ServletLoginUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
																		    	<!-- Um componente "href" ele sempre dispara um get -->
																				<img alt="Imagem User" id="fotobase64" src="${modelLogin.fotouser}" width="70px">
																			</a>
																		</c:if>
																		
																		<c:if test="${modelLogin.fotouser == '' || modelLogin.fotouser == null}">
																			<img alt="Imagem User" id="fotobase64" src="assets\images\user2.png" width="70px">
																		</c:if>
																</div>
																<input type="file" id="fileFoto" name="fileFoto" accept="image/*" onchange="visualizarImg('fotobase64', 'fileFoto');" class="form-control-file" style="margin-top: 15px; margin-left: 5px;">
															</div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="dataNascimento" id="dataNascimento" class="form-control" required="required" value="${modelLogin.dataNascimento}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Dat. Nascimento:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email:</label>
                                                            </div>
															<div class="form-group form-default form-static-label">
																<select  class="form-control" aria-label="Default select example" name="perfil">
																	<option disabled="disabled" >[Selecione o perfil]</option>
																	
																	<option value="ADMIN" <%
																		
																	ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																	
																	if(modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
																		out.print(" ");
																		// Quando for um ADMIN, isso pega o atributo e mantem selcionado 
																		out.print("selected=\"selected\"");
																		out.print(" ");
																	}%>>Admin</option>
																	
																	<option value="SECRETARIA" <%
																		
																	modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																	
																	if(modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																	}%>>Secret�ria</option>
																	
																	<option value="AUXILIAR" <%
																		
																	modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																	
																	if(modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																	}%>>Auxiliar</option>
																</select>
																<span class="form-bar"></span>
																<label class="float-label">Perfil:</label>
															</div>
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="rendamensal" id="rendamensal" class="form-control" required="required" value="${modelLogin.rendamensal}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Renda Mensal:</label>
                                                            </div>
															<div class="form-group form-default form-static-label">
                                                                <input onblur="pesquisaCep();" type="text" name="cep" id="cep" class="form-control" required="" autocomplete="off" value="${modelLogin.cep}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Cep </label>
                                                            </div>
                                                            
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="logradouro" id="logradouro" class="form-control" required="" autocomplete="off" value="${modelLogin.logradouro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Logradouro </label>
                                                            </div>
                                                            
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="bairro" id="bairro" class="form-control" required="" autocomplete="off" value="${modelLogin.bairro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Bairro </label>
                                                            </div>
                                                            
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="localidade" id="localidade" class="form-control" required="" autocomplete="off" value="${modelLogin.localidade}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Localidade </label>
                                                            </div>
                                                            
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="uf" id="uf" class="form-control" required="" autocomplete="off" value="${modelLogin.uf}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Estado </label>
                                                            </div>
                                                            
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="numero" id="numero" class="form-control" required="" autocomplete="off" value="${modelLogin.numero}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">N�mero </label>
                                                            </div>
															
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="login" id="login" class="form-control" required="" autocomplete="off" value="${modelLogin.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login </label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="password" name="senha" id="senha" class="form-control" required="" autocomplete="off"value="${modelLogin.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                             	<input type="radio" checked="checked" name="sexo" value="MASCULINO"<%
																		
																	modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																	
																	if(modelLogin != null && modelLogin.getSexo().equals("MASCULINO")){
																		out.print(" ");
																		out.print("checked=\"checked\"");
																		out.print(" ");
																	}%>> Masculino</>
																	
                                                            	<input type="radio" name="sexo" value="FEMININO" <%
																		
																	modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																	
																	if(modelLogin != null && modelLogin.getSexo().equals("FEMININO")){
																		out.print(" ");
																		out.print("checked=\"checked\"");
																		out.print(" ");
																	}%>> Feminino</>
                                                            </div>
                                                            
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
                                                            <button class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDelete();">Excluir</button>
												            
												            <c:if test="${modelLogin.id > 0}">
												            	<a href="<%= request.getContextPath()%>/ServletFones?iduser=${modelLogin.id}" class="btn btn-primary waves-effect waves-light">Telefone</a>
												            </c:if>
												            
												            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>  
												        </form>

													</div>
												</div>
											</div>
										</div>

										<span id="msg">${msg}</span>
										
										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaresultadosview">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Perfil</th>
														<th scope="col">Sexo</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items='${modelLogins}' var='ml'>
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><c:out value="${ml.perfil}"></c:out></td>
															<td><c:out value="${ml.sexo}"></c:out></td>
															<td><a class="btn btn-success" href="<%=request.getContextPath()%>/ServletLoginUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

										<nav aria-label="Navega��o de p�gina exemplo">
											<ul class="pagination">
												<%
												int totalPagina = (int) request.getAttribute("totalPagina");
												
												for(int p = 0; p < totalPagina; p++){
													String url = request.getContextPath() + "/ServletLoginUsuarioController?acao=paginar&pagina=" + (p * 5);
													out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\">"+(p+1)+"</a></li>");
												}
												%>
											</ul>
										</nav>

									</div>
								</div>
									<!-- Page-body end -->
							</div>
							<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="javascriptfile.jsp"></jsp:include>	
	<!-- Modal -->
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de usu�rio</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
						</div>
					</div>

					<div style="height: 300px; overflow: scroll;">
						<table class="table" id="tabelaresultados">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">Nome</th>
									<th scope="col">Ver</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
  
					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">

						</ul>
					</nav>

					<span id="totalResultados"></span>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		
		$("#rendamensal").maskMoney({showSymbol:true, symbol:"R$ ", decimal:",", thousands:"."});
		
		const formatter = new Intl.NumberFormat('pt-Br', {
			currency : 'BRL',
			minimumFractionDigits : 2
		});
		
		$("#rendamensal").val(formatter.format($("#rendamensal").val));
		$("#rendamesal").focus;
		
		var dataNascimento = $("#dataNascimento").val();
		
		if(dataNascimento != null && dataNascimento != ''){ 
			var dateFormat = new Date(dataNascimento);
			
			$("#dataNascimento").val(dateFormat.toLocaleDateString('pt_BR',{timeZone: 'UTC'}))
		}
		$("#nome").focus();
		
		$( function() {
		  
			$("#dataNascimento").datepicker({
			    dateFormat: 'dd/mm/yy',
			    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
			    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
			    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			    nextText: 'Pr�ximo',
			    prevText: 'Anterior'
			});
		});
	
		// Fun��o que faz que o campo s� aceite numero
		$("#numero").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
		
		$("#cep").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
	
		function pesquisaCep() {
		    var cep = $("#cep").val();
		    
		    $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) { 
	
				if (!("erro" in dados)) {
					$("#cep").val(dados.cep);
					$("#logradouro").val(dados.logradouro);
				    $("#bairro").val(dados.bairro);
				    $("#localidade").val(dados.localidade);
				    $("#uf").val(dados.uf);
				}
		    });
		}
	
		function visualizarImg(fotobase64, fileFoto) {
			
			var preview = document.getElementById(fotobase64);// Campo IMG html
			var fileUser = document.getElementById(fileFoto).files[0];
			var reader = new FileReader();
			//reader serve para ler arquivo de texto.
			//onload � usado para executar uma fun��o JavaScript imediatamente ap�s o carregamento de uma p�gina.
			reader.onloadend = function() {
				preview.src = reader.result;// Carrega a foto na tela  
			};
			
			if(fileUser){
				reader.readAsDataURL(fileUser);// Preview da imagem
			}else {
				preview.src = '';
			}
		}
	
		function verEditar(id) {
		    var urlAction = document.getElementById('formUser').action;
		    
		    window.location.href = urlAction + '?acao=buscarEditar&id='+id;
		}
		
		//Aqui chama por pagina��o e monta de novo a pagina��o chamando a fun��o ajax de novo.
		function buscaUserPagAjax(url){
		    var urlAction = document.getElementById('formUser').action;
		    var nomeBusca = document.getElementById('nomeBusca').value;
		    
			$.ajax({	     
			    method: "get",
			    url : urlAction,
			    data : url,
			    success: function (response, textStatus, xhr) {
				 
				    var json = JSON.parse(response);
					 
					$('#tabelaresultados > tbody > tr').remove();
					$("#ulPaginacaoUserAjax > li").remove();
					 
					for(var p = 0; p < json.length; p++){
					    $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td> '+json[p].nome+
					    		 '</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td></tr>');
					}
					  
					document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
					 
					var totalPagina = xhr.getResponseHeader("totalPagina");
					    
					for (var p = 0; p < totalPagina; p++){
						var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
						   
						$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
					}
			   }
			     
			}).fail(function(xhr, status, errorThrown){
			    alert('Erro ao buscar usu�rio por nome: ' + xhr.responseText);
			});
		}
		
		//Busca o usu�rio e monta a pagina��o com outra fun��o ajax.
		function buscarUsuario() {
			var nomeBusca = document.getElementById('nomeBusca').value;
		    
		    if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') { /*Validando que tem que ter valor pra buscar no banco*/
		    	var urlAction = document.getElementById('formUser').action;
				
				$.ajax({
					method: "get",
				    url : urlAction,
				    data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
				    success: function (response, textStatus, xhr) {
						var json = JSON.parse(response);
					 
						console.info(json);
						
						
						$('#tabelaresultados > tbody > tr').remove();
						$("#ulPaginacaoUserAjax > li").remove();
					 
						for(var p = 0; p < json.length; p++){
						      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td> '+json[p].nome+
						    		  '</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn btn-info">Ver</button></td></tr>');
						}
					  
						document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
					  
						var totalPagina = xhr.getResponseHeader("totalPagina");
					    
						for (var p = 0; p < totalPagina; p++){
							var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+(p * 5);
						   
						    $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
						}
				    }
				
				}).fail(function(xhr, status, errorThrown){
				    alert('Erro ao buscar usu�rio por nome: ' + xhr.responseText);
				});
		    }
		}
		
		
		function criarDeleteComAjax() {
		    
			if (confirm('Deseja realmente excluir os dados?')){
			
				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;
			 
			 	$.ajax({
			    	method: "get",
			     	url : urlAction,
			     	data : "id=" + idUser + '&acao=deletarajax',
			     	success: function (response) {
				 
				  		limparForm();
				  		document.getElementById('msg').textContent = response;
			    	}
			     
			 	}).fail(function(xhr, status, errorThrown){
			    	alert('Erro ao deletar usu�rio por id: ' + xhr.responseText);
			 	});
		    }
		}
		
		
		function criarDelete() {
			
			if(confirm('Deseja realmente excluir os dados?')) {
				
				document.getElementById("formUser").method = "get";
				document.getElementById("acao").value = "deletar";
				document.getElementById("formUser").submit();
			}
		}
		
		function limparForm() {
			var elementos = document.getElementById("formUser").elements;
			
			for(p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	
	</script>

</body>

</html>
