package com.test.game.core.concurrent;

import java.util.concurrent.Executor;

public interface IUser {

  Thread thread();

  Executor executor();
}