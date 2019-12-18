var context = new Context(rootScope);

context.init();

Context.prototype.init=function() {

	Context.prototype.userVariables = cuixbUserVariables;
}