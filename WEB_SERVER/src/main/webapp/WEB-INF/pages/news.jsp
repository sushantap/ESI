<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Jumbotron Template for Bootstrap</title>
<link href="resources/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/dist/css/jumbotron.css" rel="stylesheet">
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div class="navbar-collapse collapse">
				<form class="navbar-form navbar-right" role="form">
					<div class="form-group">
						<input type="text" placeholder="Email" class="form-control">
					</div>
					<div class="form-group">
						<input type="password" placeholder="Password" class="form-control">
					</div>
					<button type="submit" class="btn btn-success">Sign in</button>
				</form>
			</div>
		</div>
	</div>
	<esi:include src="/WEB-SERVER1/heading"
		alt="http://bak.example.com/2.html" onerror="continue" />

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<esi:include src="/WEB-SERVER1/article1"
				alt="http://bak.example.com/2.html" onerror="continue" />
			<esi:include src="/WEB-SERVER1/article2"
				alt="http://bak.example.com/2.html" onerror="continue" />
			<esi:include src="/WEB-SERVER1/article3"
				alt="http://bak.example.com/2.html" onerror="continue" ttl=50/>
		</div>
		<hr>
		<footer>
			<p>&copy; Company 2014</p>
		</footer>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="resources/dist/js/bootstrap.min.js"></script>
</body>
</html>
