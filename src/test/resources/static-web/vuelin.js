console.log('*** VUELIN ***');

var session = {
  global: {},
  page: {}
}

session.global = {
  customer: null
}

function loadPage(pageName, resolve, reject) {
  var url = "/" + pageName;
  var body = session.global;
  var reqCfg = {};
  axios.post(url, body, reqCfg)
  .then(function (response) {
    console.log(JSON.stringify(response));
    var viewChange = response.data;
    var component = {};
    component.name = pageName;
    component.template = viewChange.newPageTemplate; 
    resolve(component);
  })
  .catch(function (error) {
    console.log(JSON.stringify(error));
    reject(error);
  });
}

// 1. Define route components.

const loadWelcomePageComponent = function (resolve, reject) {
  loadPage("WelcomePage", resolve, reject);
}


const ProductPage = { template: '<h4>ProductPage</h4>' }

const SummaryPage = { template: '<h4>SummaryPage</h4>' }

const ErrorPage = { template: '<h4>Oops, something went wrong!</h4>' }

// 2. Define some routes
// Each route should map to a component. The "component" can
// either be an actual component constructor created via
// `Vue.extend()`, or just a component options object.
// We'll talk about nested routes later.
const routes = [
    { path: '/', redirect: '/welcome' },
    { path: '/welcome', component: loadWelcomePageComponent },
    { path: '/product', component: ProductPage },
    { path: '/summary', component: SummaryPage },
    { path: '/error', component: ErrorPage }
]

// 3. Create the router instance and pass the `routes` option
// You can pass in additional options here, but let's
// keep it simple for now.
const router = new VueRouter({
  routes // short for `routes: routes`
});

// 4. Create and mount the root instance.
// Make sure to inject the router with the router option to make the
// whole app router-aware.
const vue = new Vue({
  router
}).$mount('#app-root');

/*
*/
