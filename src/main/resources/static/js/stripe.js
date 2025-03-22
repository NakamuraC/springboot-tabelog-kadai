const stripe = Stripe('pk_test_51QpqOTIZEzuZH2VcBW0nLQ1I6HXFOkYXPRhzVzzb8PWRar0vVYPaaCOf434Tqso0Va0b9cAs9RWL64gvusrA33we00EL8JsZsa');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });