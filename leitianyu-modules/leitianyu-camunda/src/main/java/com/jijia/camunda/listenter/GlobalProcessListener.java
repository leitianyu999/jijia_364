package com.jijia.camunda.listenter;

import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.impl.util.xml.Element;
/**
 *  * 本项目为Apache2.0协议 请保留此协议头 ,否则即为违反Apache2.0协议,可以视为侵权
 *  * @author Dr4JavaEE
 */
public class GlobalProcessListener  extends AbstractBpmnParseListener {
    public final static ExecutionListener EXECUTION_LISTENER = new CamundaGlobalListenerDelegate();
    public final static TaskListener TASK_LISTENER = new CamundaGlobalListenerDelegate();

    @Override
    public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {
        startEventActivity.addListener(ExecutionListener.EVENTNAME_START, EXECUTION_LISTENER);
    }

    @Override
    public void parseEndEvent(Element endEventElement, ScopeImpl scope, ActivityImpl activity) {
        activity.addListener(ExecutionListener.EVENTNAME_START, EXECUTION_LISTENER);
    }

    @Override
    public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
        activity.addListener(ExecutionListener.EVENTNAME_START, EXECUTION_LISTENER);
        UserTaskActivityBehavior activityBehavior = (UserTaskActivityBehavior) activity.getActivityBehavior();
        TaskDefinition taskDefinition = activityBehavior.getTaskDefinition();
        taskDefinition.addTaskListener(TaskListener.EVENTNAME_CREATE, TASK_LISTENER);
        taskDefinition.addTaskListener(TaskListener.EVENTNAME_ASSIGNMENT, TASK_LISTENER);
        taskDefinition.addTaskListener(TaskListener.EVENTNAME_COMPLETE, TASK_LISTENER);
    }
}
