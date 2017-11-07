Handlebars.registerHelper('test', function (context) {
   return 'Hi ' + context.name;
});

Handlebars.registerHelper('test2', function (context) {
   return 'Greetings ' + context.name;
});