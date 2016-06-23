/*
* Copyright 2015 herd contributors
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.finra.herd.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.retry.RetryPolicy.BackoffStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.finra.herd.core.helper.ConfigurationHelper;

public class RetryPolicyFactoryTest
{
    @InjectMocks
    private RetryPolicyFactory retryPolicyFactory;

    @Mock
    private ConfigurationHelper configurationHelper;

    @Mock
    private BackoffStrategy backoffStrategy;

    @Before
    public void before()
    {
        initMocks(this);
    }

    /**
     * Asserts that the retry policy generated by the factory is configured with the correct values.
     */
    @Test
    public void assertResultRetryPolicyConfiguredCorrectly()
    {
        int expectedMaxErrorRetry = 12345;

        when(configurationHelper.getProperty(any(), eq(Integer.class))).thenReturn(expectedMaxErrorRetry);

        RetryPolicy retryPolicy = retryPolicyFactory.getRetryPolicy();

        assertEquals(PredefinedRetryPolicies.DEFAULT_RETRY_CONDITION, retryPolicy.getRetryCondition());
        assertEquals(backoffStrategy, retryPolicy.getBackoffStrategy());
        assertEquals(expectedMaxErrorRetry, retryPolicy.getMaxErrorRetry());
    }
}
