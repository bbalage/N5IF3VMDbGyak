<Query Kind="Statements" />

int[] nums = new int[11];
for (int i = 0; i < nums.Length; i++) {
	nums[i] = i;
}

//nums.Where(num => num % 2 == 0).Select(num => num * num).Dump("b");

// nums.Aggregate(0, (sum, num) => sum += num, ).Select(sum => sum / nums.Length);

// nums.Average().Dump("c");

// nums.Where(num => num > nums.Average()).Select(num => new {num, Diff = num - nums.Average()}).Dump("d");

nums.GroupBy(num => Math.Abs(num - nums.Average()), num => num).Dump("e");

