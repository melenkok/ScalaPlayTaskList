
console.log("Running Js");
$("#randomText").click(function() {
       // $("#random").html("Nan")  instead of this we can load some other result of a route
        $("#random").load("/random") //now we need a method that has a route like this
});


$("#randomStringText").click(function() {
        const stringText = document.getElementById("lenghtValue").value;
        $("#randomString").load("/randomString/" + stringText) // instead of this we can load some other result of a route
       // $("#random").load("/random") //now we need a method that has a route like this
});

// With fetch

const stringTextFetch = document.getElementById("randomStringTextFetch");
stringTextFetch.onclick = () => {
	const lengthInput = document.getElementById("lenghtValueFetch");
	const url = "/randomString/" + lengthInput.value;
	console.log(url);
	fetch(url).then((response) => {
		return response.text();
	}).then((responseText) => {
		const randomString = document.getElementById("randomStringFetch");
		randomString.innerHTML = responseText;
	})
};