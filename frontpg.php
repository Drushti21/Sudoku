<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="stylepg.css">
    <style>
      @import url('https://fonts.googleapis.com/css2?family=Agbalumo&family=Amita:wght@400;700&family=Bree+Serif&family=DM+Serif+Text:ital@0;1&family=Inconsolata:wght@500&family=Josefin+Sans:wght@500&family=Nunito:ital,wght@0,200;0,300;0,400;0,500;1,200;1,300;1,400&family=Poppins:ital,wght@1,100&display=swap');
    </style>
    <title>SUDOKU</title>
  </head>
  <body>
    <div class="frontbt">
      <h1 class="text-center">SUDOKU</h1>
      <h3 class="text-center">Select your level</h3>
      
      <button class="btn" onclick="redirectToJavaFile('easy')">Easy</button>
      <button class="btn" onclick="redirectToJavaFile('medium')">Medium</button>
      <button class="btn" onclick="redirectToJavaFile('hard')">Hard</button>
    </div>

    <script>
      function redirectToJavaFile(level) {
        // Replace the following with the correct path to your Java file or Java servlet
        let url = '';
        
        if (level === 'easy') {
          url = 'SudokuGameGUI.java';  // Adjust path as needed
        } else if (level === 'medium') {
          url = 'path_to_your_medium_java_file';  // Adjust path as needed
        } else if (level === 'hard') {
          url = 'path_to_your_hard_java_file';  // Adjust path as needed
        }
        
        // Redirect to the appropriate URL
        window.location.href = url;
      }
    </script>
  </body>
</html>
