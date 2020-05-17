function greet(name, element) {
    console.log("Hi, " + name);
    element.$server.greet("server");
}

function javascriptFunction(element) {
    element.$server.javaFunction(window.innerWidth);
}