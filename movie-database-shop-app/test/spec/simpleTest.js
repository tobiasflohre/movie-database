'use strict';

var training = {
	add: function(a, b) {
		return a + b;
	}
};

describe('training', function() {
	describe('add', function() {
		it('should add two positive numbers', function() {
			expect(training.add(1, 2)).toBe(3);
		});

		it('should ignore third to nth argument', function() {
			expect(training.add(1, 2, 3)).toBe(3);
		});
	});
});
