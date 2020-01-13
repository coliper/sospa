package org.coliper.sospa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.coliper.lontano.AbstractLontanoService;
import org.coliper.lontano.impl.JavalinLontanoService;

import com.google.common.base.Preconditions;

import io.javalin.Javalin;

public class Sospa<T> {
	private static final String DEFALUT_ROOT_PATH = "";

	private final String rootPath = DEFALUT_ROOT_PATH;
	private final Class<T> globalViewDataType;
	private final Supplier<T> globalViewObjectSupplier;
	private final AbstractLontanoService<?> lontano;
	// move both to builder as not needed later?
	private List<SospaPage<T, ?, ?>> pageList = new ArrayList<>();
	private List<SospaCommonApi> apiList = new ArrayList<>();
	private ApiMethodMatcher apiMethodMatcher = new ApiMethodMatcher();
	private ServersideRenderingEngine renderingEngine = new ThymeleafRenderingEngine();

	private Sospa(Class<T> globalViewDataType, Supplier<T> globalViewObjectSupplier, Javalin javalin) {
		this.globalViewDataType = globalViewDataType;
		this.globalViewObjectSupplier = globalViewObjectSupplier;
		this.lontano  = new JavalinLontanoService();
	}

	public Sospa(Class<T> globalViewDataType, Javalin javalin) {
		this(globalViewDataType, FunctionUtil.createDefaultConstructorSupplier(globalViewDataType), javalin);
	}

	public Sospa<T> addPage(SospaPage<T, ?, ?> page) {
		Objects.requireNonNull(page, "page");
		Preconditions.checkArgument(
				this.pageList.stream().filter(p -> p.getPageName().equals(page.getPageName())).count() == 0,
				"Page with name '" + page.getPageName().getNameAsString() + "' was already added.");
		this.pageList.add(page);
		return this;
	}

	public Sospa<T> addPages(Collection<SospaPage<T, ?, ?>> pages) {
		for (SospaPage<T, ?, ?> vuelinPage : pages) {
			this.addPage(vuelinPage);
		}
		return this;
	}

	private void registerEndpoints() {
		for (SospaPage<T, ?, ?> page : this.pageList) {
			this.registerPageEndpoints(page);
		}
	}

	private void registerPageEndpoints(SospaPage<T, ?, ?> page) {
		this.registerPageLoadEndpoint(page);
		this.registerPageMethodEndpoints(page);
	}

	private void registerPageMethodEndpoints(SospaPage<T, ?, ?> page) {
		// TODO Auto-generated method stub

	}

	private void registerPageLoadEndpoint(SospaPage<T, ?, ?> page) {
//		PageLoadEndpoint<T, ?, ?> endpoint = new PageLoadEndpoint<>(this.rootPath, page, this.vuelin);
//		endpoint.registerWithJavalin(this.vuelin.javalin);
	}

	ServersideRenderingEngine renderingEngine() {
		return this.renderingEngine;
	}

	Class<T> globalViewDataType() {
		return globalViewDataType;
	}

	Supplier<T> globalViewObjectSupplier() {
		return globalViewObjectSupplier;
	}
}
