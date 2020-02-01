console.log('*** SOSPA ***');

// LONTANO BEGIN

var lontano = {
}

lontano.axios = axios.create({
  baseURL: '/'
});

lontano.call = function (interface, operation, params) {
  console.log('lontano.call: interface=' + interface + ' operation=' + operation + ' params=' + JSON.stringify(params));
  var url = interface + '/' + operation;
  var body = JSON.stringify(params);
  return lontano.axios.post(url, body);
}

lontano.api = {};
lontano.api.WelcomePage = {};
lontano.api.WelcomePage.openFromClient = function (p1) {
  return lontano.call('WelcomePage', 'openFromClient', [p1]);
};
lontano.api.ProductPage = {};
lontano.api.ProductPage.openFromClient = function (p1) {
  return lontano.call('WelcomePage', 'openFromClient', [p1]);
};


// LONTANO END

var sospa = {};

sospa.globalData = {};

sospa.handleViewChangeSync = function (page, viewChange) {
  console.log('sospa.handleViewChangeSync: page=' + JSON.stringify(page) + ' viewChange=' + JSON.stringify(viewChange));
  if (viewChange.pageHtmlSource) {
    page.template = viewChange.pageHtmlSource;
  }
  if (viewChange.pageData) {
    page.data.p = viewChange.pageData;
  }
  if (viewChange.globalData) {
    sospa.globalData = viewChange.globalData;
  }
}

sospa.handleViewChange = function (page, promise) {
  console.log('sospa.handleViewChange: page=' + JSON.stringify(page));
  return promise.then(function (viewChange) {
    sospa.handleViewChangeSync(page, viewChange);
  }).catch(function (error) {
    console.log(JSON.stringify(error));
  });
}

sospa.loadPage = function (page, resolve, reject) {
  console.log('sospa.loadPage: page=' + JSON.stringify(page));
  page.methods.openFromClient(page.data.g, null).then(function (response) {
    resolve(page);
  }).catch(function (error) {
    reject(error);
  });

}

sospa.pages = {};

sospa.pages.WelcomePage = {
  name: 'WelcomePage',
  template: '<p>Loading WelcomePage ...</p>',
  data: {
    p: {},
    g: sospa.globalData
  },
  methods: {}
}

sospa.pages.WelcomePage.methods.openFromClient = function (p1) {
  return sospa.handleViewChange(sospa.pages.WelcomePage, lontano.api.WelcomePage.openFromClient(p1));
}

sospa.vue = {};

// 1. Define route components.

sospa.vue.components = {};
sospa.vue.components.WelcomePage = function (resolve, reject) {
  sospa.loadPage(sospa.pages.WelcomePage, resolve, reject);
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
