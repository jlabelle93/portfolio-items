<!DOCTYPE html>
<html>
	<head>
		<title>Records found in Database</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="design.css">
	</head>
	<body>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
		<script src="main.js"></script>
		<nav class="navbar navbar-expand-md bg-dark navbar-dark">
			<a class="navbar-brand" href="#">JLabelle Portfolio</a>
			<button class="navbar-toggler navbar-dark" type="button" data-toggle="collapse" data-target="#main-navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="main-navigation">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link" href="index.html">Home</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="wordpress.html">WordPress Blog</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="formvaldemo.html">Form Validation Demo</a>
					</li>
					<li class="active nav-item">
						<a class="nav-link" href="databaseFormReport.html">Database Demo</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="ajaxDemo.html">AJAX Demo</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="jqueryDemo.html">JQuery Demo</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="resumePage.html">Resume</a>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sample Projects</a>
						<div class="dropdown-menu navbar-dark bg-dark" aria-labelledby="navbarDropdown">
							<a class="dropdown-item text-light" href="project1.html">Project 1</a>
						</div>
					</li>
				</ul>
			</div>
		</nav>
		<header class="page-header header container-fluid">
			<div class="overlay"></div>
				<h1>The following records were found in the database:</h1>
					<div class="table-responsive table-scroll">
					<table class="table table-dark table-striped table-hover table-bordered">
						<tr>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Email</th>
						</tr>
				<?php 
					$mysqli = mysqli_connect("69.172.204.200", "jlabelle93", "KindlyW@t", "jlabelle_db");
			
					if(!$mysqli) {
						die("Connection to the database failed: " . $mysqli -> connect_error);
					}
	
					$query = "SELECT * FROM demoDB";
					$result = mysqli_query($mysqli, $query);
					while ($row = mysqli_fetch_assoc($result)) {
						echo "<tr>";
						foreach($row as $field => $value) {
							echo "<td>".htmlentities($value)."</td>";
						}
						echo "</tr>";
					}
					$mysqli -> close();
				 ?>
					</table>
					</div>
		</header>
		<footer class="page-footer">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-8 col-md-8 col-sm-12">
						<h6 class="text-uppercase font-weight-bold">Additional Information</h6>
						<p>This website was for a class project while in attendance at Okanagan College. 
						<br/>It serves as a hub for my projects and work as a student.</p>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-12">
						<h6 class="text-uppercase font-weight-bold">Contact</h6>
						<p>Jacob Labelle
						<br/><a href="mailto:jacob.labelle93@gmail.com" target="_blank">Email Me</a>
						<br/><a href="https://www.linkedin.com/in/jacob-labelle-3b9740201/" target="_blank">My LinkedIn Profile</a>
						<br/><a href="https://github.com/jlabelle93" target="_blank">GitHub Profile</a></p>
					</div>
				</div>
			</div>
			<div class="footer-copyright text-center">Created by Jacob Labelle FALL 2020</div>
		</footer>
	</body>
</html>
