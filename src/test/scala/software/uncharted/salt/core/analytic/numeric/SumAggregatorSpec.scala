/*
 * Copyright 2016 Uncharted Software Inc.
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

package software.uncharted.salt.core.analytic.numeric

import org.scalatest._
import software.uncharted.salt.core.analytic.numeric._

class SumAggregatorSpec extends FunSpec {
  describe("SumAggregator") {
    describe("#default()") {
      it("should have a default value of 0") {
        assert(SumAggregator.default.equals(0D))
      }
    }

    describe("#add()") {
      it("should return 0 if a NaN is added to the default value") {
        assert(SumAggregator.add(SumAggregator.default, Some(Double.NaN)) === 0)
      }
      it("should retain the current value when a null record is passed in") {
        assert(SumAggregator.add(SumAggregator.default, None).equals(SumAggregator.default))
        var test = Math.random
        assert(SumAggregator.add(test, None).equals(test))
      }
      it("should sum the current value and new value when a new value is passed in") {
        var test = Math.random
        assert(SumAggregator.add(SumAggregator.default, Some(test)) === SumAggregator.default + test)

        var test2 = Math.random
        assert(SumAggregator.add(test, Some(test2)) === test + test2)
      }
    }

    describe("#merge()") {
      it("should combine two counts using max()") {
        var left = Math.random()
        var right = Math.random()
        assert(SumAggregator.merge(left, right) === left + right)
      }
    }

    describe("#finish()") {
      it("should convert the intermediate value into a Double") {
        var test = Math.random
        assert(SumAggregator.finish(test).isInstanceOf[Double])
        assert(SumAggregator.finish(test).equals(test))
      }
    }
  }
}
