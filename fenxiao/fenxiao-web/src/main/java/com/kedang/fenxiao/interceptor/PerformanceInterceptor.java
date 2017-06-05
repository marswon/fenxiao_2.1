package com.kedang.fenxiao.interceptor;




import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kedang.fenxiao.util.perf.Profiler;


/**
 * 拦截器,用于存放渲染视图时需要的的共享变量
 */
public class PerformanceInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);

	private boolean usePerformance = true;

	public void setLavalTime(long lavalTime) {
		this.lavalTime = lavalTime;
	}

	private long lavalTime = 500;
//	private ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();

	public void setUsePerformance(boolean usePerformance) {
		this.usePerformance = usePerformance;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		log.info("请注意,在这里可以存放渲染视图时需要的的共享变量");
		if (usePerformance) {
			Profiler.start("process HTTP request");
		}


		return super.preHandle(request, response, handler);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (usePerformance) {
			StringBuilder str = new StringBuilder();
			Profiler.release();
			long duration = Profiler.getDuration();

			if (duration >= lavalTime) {
				str.append(request.getRequestURI()).append("?");
				Map map = request.getParameterMap();
				if (map != null) {
					Set<Map.Entry> set = map.entrySet();
					for (Map.Entry en : set) {
						str.append(en.getKey()).append("=").append(request.getParameter((String) en.getKey())).append("&");
					}
				}
				logger.warn(MessageFormat.format("Response of {0} returned interceptor {1,number}ms\n{2}\n", new Object[]{
						str.toString(), new Long(duration), getDetail()
				}));
			}
			Profiler.reset();
		}
	}

	private String getDetail() {
		return Profiler.dump("Detail: ", "        ");
	}

}
