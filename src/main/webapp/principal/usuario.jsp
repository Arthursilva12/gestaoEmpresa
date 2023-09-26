<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">


<jsp:include page="head.jsp"></jsp:include>

<body>
	<div>
		
	</div>

	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<!-- Barra de navegação abaixo da foto -->
			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<!-- menu de navegação abaixo da foto -->
					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start/ onde está a estrutura -->
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
														<h4 class="sub-title">Cad. Usuário</h4>

														<form class="form-material" action="<%= request.getContextPath()%>/ServletLoginUsuarioControler" method="post" id="formUser">
															<input type="hidden" name="acao" id="acao" value="">
														
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome:</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${modelLogin.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Email:</label>
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
                                                            
                                                            <button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
                                                            <button class="btn btn-success waves-effect waves-light">Salvar</button>
												            <button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDelete();">Excluir</button>
												            <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#exampleModal">Pesquisar</button>                                                       
												        </form>

													</div>
												</div>
											</div>
										</div>

										<span id="msg">${msg}</span>
										
										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaresultadosviw">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items='${modelLogins}' var='ml'>
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-success" href="<%=request.getContextPath()%>/ServletLoginUsuarioControler?acao=buscarEditar&id=${ml.id}">Ver</a></td>
														</tr>
													</c:forEach>														
												</tbody>
											</table>
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
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
					<button type="button" class="close" data-dismiss="modal"aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-success" type="button" onclick="buscarUsuario();">Busca</button>
						</div>
					</div>
					<div style="height: 300px;overflow: scroll;">
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
					<span id="totalResultado"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
		function verEditar(id) {
			var urlAction = document.getElementById('formUser').action;
			// Redirecionamento de pagina com Java Script
			window.location.href = urlAction + '?acao=buscarEditar&id='+id;
		}
	
	
		function buscarUsuario() {
			var nomeBusca = document.getElementById('nomeBusca').value;
			// Validando que tem que ter valor pra bucar no banco.
			if(nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
				
				var urlAction = document.getElementById('formUser').action;
				
				$.ajax({
					method: "get", 
					url : urlAction,
					data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
					success: function (response) {
						// converte um valor para uma notação JSON
						var json = JSON.parse(response);
						console.log(json);
						
						$('#tabelaresultados > tbody > tr').remove();
	
						for(var p = 0; p < json.length; p++) {
							$('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td>'+
								json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+');" type="button" class="btn btn-info">Ver</button></td></tr>');
						}
						
						document.getElementById('totalResultado').textContent = 'resultados: ' + json.length;
					}
					
				}).fail(function(xhr, status, errorThrown){
					alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
				});
			}
		}
	
		
		function deleteComAjax() {
			if(confirm('Deseja realmente excluir os dados?')) {
				
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
					// xhr, é onde mostrara o erro.
					alert('Erro ao deletar usuario por id: ' + xhr.responseText);
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
