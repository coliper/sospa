console.log('*** SOSPA ***');

// LONTANO BEGIN

var lontano = {
}

lontano.axios = axios.create({
    baseURL: '/'
});
    
lontano.call = function(interface, operation, params) {
  var url = interface + '/' + operation;
  var body = JSON.stringify(params);
  return lontano.axios.post(url, body);
}

lontano.api = {};
lontano.api.WelcomePage = {};
lontano.api.WelcomePage.open = function(p1, p2) {
  return lontano.call('WelcomePage', 'open', [p1, p2]);
};
lontano.api.ProductPage = {};
lontano.api.ProductPage.open = function(p1, p2) {
  return lontano.call('WelcomePage', 'open', [p1, p2]);
};


// LONTANO END

var sospa = {};

sospa.globalData = {};

sospa.handleViewChangeSync = function (page, viewChange) {
  page.template = viewChange.pageHtmlSource;
}

sospa.handleViewChange = function (page, promise) {
  return promise.then(function(viewChange) {
    sospa.handleViewChangeSync(page, viewChange);
  })
}

sospa.loadPage = function (pageName, resolve, reject) {
  lontano.api[pageName].open.then(function (response) {
    console.log(JSON.stringify(response));
    var viewChange = response.data;
    var component = {};
    component.name = pageName;
    component.template = viewChange.newPageTemplate; 
    resolve(sospa.pages[pageName]);
  })
  .catch(function (error) {
    console.log(JSON.stringify(error));
    reject(error);
  });
}

sospa.pages = {};

sospa.pages.WelcomePage = {
  name: 'WelcomePage',
  template: '<p>Loading WelcomePage ...</p>',
  data: {},
  methods: {}
}

sospa.pages.WelcomePage.methods.open = function(p1, p2) {
  return sospa.handleViewChange('WelcomePage', lontano.api.WelcomePage.open(p1, p2));
}

sospa.vue = {};

// 1. Define route components.

sospa.vue.components = {};
sospa.vue.components.WelcomePage = function (resolve, reject) {
  loadPage("WelcomePage", resolve, reject);
}


sospa.vue.components.ProductPage = { template: '<h4>ProductPage</h4>' }

sospa.vue.components.SummaryPage = { template: '<h4>SummaryPage</h4>' }

sospa.vue.components.ErrorPage = { template: '<h4>Oops, something went wrong!</h4>' }

// 2. Define some routes
// Each route should map to a component. The "component" can
// either be an actual component constructor created via
// `Vue.extend()`, or just a component options object.
// We'll talk about nested routes later.
sospa.vue.routes = [
    { path: '/', redirect: '/welcome' },
    { path: '/welcome', component: sospa.vue.components.WelcomePage },
    { path: '/product', component: sospa.vue.components.ProductPage },
    { path: '/summary', component: sospa.vue.components.SummaryPage },
    { path: '/error', component: sospa.vue.components.ErrorPage }
]

// 3. Create the router instance and pass the `routes` option
// You can pass in additional options here, but let's
// keep it simple for now.
sospa.vue.router = new VueRouter({
  routes: sospa.vue.routes 
});

// 4. Create and mount the root instance.
// Make sure to inject the router with the router option to make the
// whole app router-aware.
sospa.vue.instance = new Vue({
  router: sospa.vue.router
}).$mount('#app-root');

/*
*/
