package com.kedang.fenxiao.util.perf;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: skyrain
 * Date: 13-7-6
 * Time: 下午1:21
 */
public class Profiler {

	public static interface Message
	{

//		public abstract MessageLevel getMessageLevel(Entry entry);

		public abstract String getBriefMessage();

		public abstract String getDetailedMessage();
	}

//	public static final class MessageLevel extends IntegerEnum
//	{
//
//		private static final long serialVersionUID = 0x2d36353838323839L;
//		public static final MessageLevel NO_MESSAGE = (MessageLevel)create();
//		public static final MessageLevel BRIEF_MESSAGE = (MessageLevel)create();
//		public static final MessageLevel DETAILED_MESSAGE = (MessageLevel)create();
//
//
//		public MessageLevel()
//		{
//		}
//	}

	public static final class Entry
	{

		public String getMessage()
		{
//			String messageString = null;
//			if(message instanceof String)
//				messageString = (String)message;
//			else
//			if(message instanceof Message)
//			{
//				Message messageObject = (Message)message;
//				MessageLevel level = MessageLevel.BRIEF_MESSAGE;
//				if(isReleased())
//					level = messageObject.getMessageLevel(this);
//				if(level == MessageLevel.DETAILED_MESSAGE)
//					messageString = messageObject.getDetailedMessage();
//				else
//					messageString = messageObject.getBriefMessage();
//			}
			return (String)message;
		}

		public long getStartTime()
		{
			return baseTime <= 0L ? 0L : startTime - baseTime;
		}

		public long getEndTime()
		{
			if(endTime < baseTime)
				return -1L;
			else
				return endTime - baseTime;
		}

		public long getDuration()
		{
			if(endTime < startTime)
				return -1L;
			else
				return endTime - startTime;
		}

		public long getDurationOfSelf()
		{
			long duration = getDuration();
			if(duration < 0L)
				return -1L;
			if(subEntries.isEmpty())
				return duration;
			for(int i = 0; i < subEntries.size(); i++)
			{
				Entry subEntry = (Entry)subEntries.get(i);
				duration -= subEntry.getDuration();
			}

			if(duration < 0L)
				return -1L;
			else
				return duration;
		}

		public double getPecentage()
		{
			double parentDuration = 0.0D;
			double duration = getDuration();
			if(parentEntry != null && parentEntry.isReleased())
				parentDuration = parentEntry.getDuration();
			if(duration > 0.0D && parentDuration > 0.0D)
				return duration / parentDuration;
			else
				return 0.0D;
		}

		public double getPecentageOfAll()
		{
			double firstDuration = 0.0D;
			double duration = getDuration();
			if(firstEntry != null && firstEntry.isReleased())
				firstDuration = firstEntry.getDuration();
			if(duration > 0.0D && firstDuration > 0.0D)
				return duration / firstDuration;
			else
				return 0.0D;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List getSubEntries()
		{
			return Collections.unmodifiableList(subEntries);
		}

		private void release()
		{
			endTime = System.currentTimeMillis();
		}

		private boolean isReleased()
		{
			return endTime > 0L;
		}

		@SuppressWarnings("unchecked")
		private void enterSubEntry(Object message)
		{
			Entry subEntry = new Entry(message, this, firstEntry);
			subEntries.add(subEntry);
		}

		private Entry getUnreleasedEntry()
		{
			Entry subEntry = null;
			if(!subEntries.isEmpty())
			{
				subEntry = (Entry)subEntries.get(subEntries.size() - 1);
				if(subEntry.isReleased())
					subEntry = null;
			}
			return subEntry;
		}

		public String toString()
		{
			return toString("", "");
		}

		private String toString(String prefix1, String prefix2)
		{
			StringBuffer buffer = new StringBuffer();
			toString(buffer, prefix1, prefix2);
			return buffer.toString();
		}

		private void toString(StringBuffer buffer, String prefix1, String prefix2)
		{
			buffer.append(prefix1);
			String message = getMessage();
			long startTime = getStartTime();
			long duration = getDuration();
			long durationOfSelf = getDurationOfSelf();
			double percent = getPecentage();
			double percentOfAll = getPecentageOfAll();
			Object params[] = {
					message, new Long(startTime), new Long(duration), new Long(durationOfSelf), new Double(percent), new Double(percentOfAll)
			};
			StringBuffer pattern = new StringBuffer("{1,number} ");
			if(isReleased())
			{
				pattern.append("[{2,number}ms");
				if(durationOfSelf > 0L && durationOfSelf != duration)
					pattern.append(" ({3,number}ms)");
				if(percent > 0.0D)
					pattern.append(", {4,number,##%}");
				if(percentOfAll > 0.0D)
					pattern.append(", {5,number,##%}");
				pattern.append("]");
			} else
			{
				pattern.append("[UNRELEASED]");
			}
			if(message != null)
				pattern.append(" - {0}");
			buffer.append(MessageFormat.format(pattern.toString(), params));
			for(int i = 0; i < subEntries.size(); i++)
			{
				Entry subEntry = (Entry)subEntries.get(i);
				buffer.append('\n');
				if(i == subEntries.size() - 1)
				{
					subEntry.toString(buffer, prefix2 + "`---", prefix2 + "    ");
					continue;
				}
				if(i == 0)
					subEntry.toString(buffer, prefix2 + "+---", prefix2 + "|   ");
				else
					subEntry.toString(buffer, prefix2 + "+---", prefix2 + "|   ");
			}

		}

		@SuppressWarnings("rawtypes")
		private final List subEntries;
		private final Object message;
		private final Entry parentEntry;
		private final Entry firstEntry;
		private final long baseTime;
		private final long startTime;
		private long endTime;





		@SuppressWarnings("rawtypes")
		private Entry(Object message, Entry parentEntry, Entry firstEntry)
		{
			subEntries = new ArrayList(4);
			this.message = message;
			startTime = System.currentTimeMillis();
			this.parentEntry = parentEntry;
			if(firstEntry==null){
				this.firstEntry=this;
			}else{
				this.firstEntry=firstEntry;
			}
//			this.firstEntry = (Entry)ObjectUtil.defaultIfNull(firstEntry, this);
			baseTime = firstEntry != null ? firstEntry.startTime : 0L;
		}

	}


	public Profiler()
	{
	}

	public static void start()
	{
		start((String)null);
	}

	@SuppressWarnings("unchecked")
	public static void start(String message)
	{
		entryStack.set(new Entry(message, null, null));
	}

	@SuppressWarnings("unchecked")
	public static void start(Message message)
	{
		entryStack.set(new Entry(message, null, null));
	}

	@SuppressWarnings("unchecked")
	public static void reset()
	{
		entryStack.set(null);
	}

	public static void enter(String message)
	{
		Entry currentEntry = getCurrentEntry();
		if(currentEntry != null)
			currentEntry.enterSubEntry(message);
	}

	public static void enter(Message message)
	{
		Entry currentEntry = getCurrentEntry();
		if(currentEntry != null)
			currentEntry.enterSubEntry(message);
	}

	public static void release()
	{
		Entry currentEntry = getCurrentEntry();
		if(currentEntry != null)
			currentEntry.release();
	}

	public static long getDuration()
	{
		Entry entry = (Entry)entryStack.get();
		if(entry != null)
			return entry.getDuration();
		else
			return -1L;
	}

	public static String dump()
	{
		return dump("", "");
	}

	public static String dump(String prefix)
	{
		return dump(prefix, prefix);
	}

	public static String dump(String prefix1, String prefix2)
	{
		Entry entry = (Entry)entryStack.get();
		if(entry != null)
			return entry.toString(prefix1, prefix2);
		else
			return "";
	}

	public static Entry getEntry()
	{
		return (Entry)entryStack.get();
	}

	private static Entry getCurrentEntry()
	{
		Entry subEntry = (Entry)entryStack.get();
		Entry entry = null;
		if(subEntry != null)
			do
			{
				entry = subEntry;
				subEntry = entry.getUnreleasedEntry();
			} while(subEntry != null);
		return entry;
	}

	@SuppressWarnings("rawtypes")
	private static final ThreadLocal entryStack = new ThreadLocal();
}
