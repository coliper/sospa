console.log('*** VUELIN ***');


// 1. Define route components.
const WelcomePage = { 
    template: `
      <div>
        <h4>WelcomePage</h4>
        <button class="btn btn-primary">Next</button>
      </div>
    ` 
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
    { path: '/welcome', component: WelcomePage },
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
